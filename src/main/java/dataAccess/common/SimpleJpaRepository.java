package dataAccess.common;


import common.argumentAssert.Assert;
import dataAccess.common.exception.DataAccessException;
import dataAccess.common.exception.EntityWithSuchIdDoesNotExistsDataAccessException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaSystemException;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

public class SimpleJpaRepository<T, ID extends Serializable> implements Repository<T, ID> {


    protected EntityManager em;

    protected Class<T> entityClass;


    public SimpleJpaRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public Class<T> getEntityTypeClass() {
        return entityClass;
    }

    @Override
    public <S extends T> S save(S s) {
        Assert.notNull(s, "entity");
        return em.merge(s);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> iterable) {
        return saveLittle(iterable);
    }

    public <S extends T> List<S> saveLittle(Iterable<S> iterable) {
        Assert.notNull(iterable, "iterable of entities");
        List<S> result = new ArrayList<S>();
        for (S element : iterable) {
            result.add(this.save(element));
        }
        return result;
    }

    public <S extends T> List<S> saveLotsOf(Iterable<S> iterable) {
        Assert.notNull(iterable, "iterable of entities");
        List<S> result = new ArrayList<S>();
        int i = 0;
        for (S element : iterable) {
            result.add(this.save(element));
            if (++i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
        em.clear();
        return result;
    }


    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S s) {
        S result = this.save(s);
        this.flush();
        return result;
    }

    @Override
    public void deleteAndFlush(ID id) throws EntityWithSuchIdDoesNotExistsDataAccessException{
        Assert.notNull(id, "id");
        T entity = findOne(id);
        if (entity == null) {
            throw new EntityWithSuchIdDoesNotExistsDataAccessException(id);
        }
        deleteAndFlush(entity);
    }

    @Override
    public void deleteAndFlush(T t) {
        Assert.notNull(t, "Entity %s to deleteAndFlush must not be null", t);
        if (!em.contains(t)) {
            t = em.merge(t);
        }
        try {
            em.remove(t);
            em.flush();
        } catch(JpaSystemException e){
            Throwable te=null;
            if(e.getCause()!=null) {
                te=e.getCause();
                if(te.getCause()!=null){
                    te=te.getCause();
                    if (te.getCause() instanceof SQLIntegrityConstraintViolationException) {
                        throw new DataIntegrityViolationException("Can't delete entity beacause other entities has relationship to it", e);
                    }
                }
            }
            throw e;
        }
    }

    @Override
    public void deleteAndFlush(Iterable<? extends T> iterable) {
        Assert.notNull(iterable, "iterable of entities");
        for (T entity : iterable) {
            deleteAndFlush(entity);
        }
    }

    @Override
    public void deleteAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> cd = cb.createCriteriaDelete(getEntityTypeClass());
        cd.from(getEntityTypeClass());
        em.createQuery(cd).executeUpdate();
    }

    @Override
    public void deleteInBatch(Iterable<T> iterable) {
        deleteAndFlush(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public void clearPersistenceContext() {
        em.clear();
    }

    @Override
    public T findOne(ID id) throws DataAccessException, EntityWithSuchIdDoesNotExistsDataAccessException {
        return findOne(id,null);
    }

    public T findOne(ID id,Node<String> fetchTree) throws DataAccessException, EntityWithSuchIdDoesNotExistsDataAccessException {
        T result=doFindOne(id,fetchTree);
        if(result==null){
            throw new EntityWithSuchIdDoesNotExistsDataAccessException(id);
        }
        return result;
    }

    protected T doFindOne(ID id) {
        return doFindOne(id,null);
    }

    protected T doFindOne(ID id,Node<String> fetchTree) {
        Map<String, Object> hints =buildHintsByFetchPaths(fetchTree);
        if(hints!=null) {
            return em.find(getEntityTypeClass(), id,hints);
        } else{
            return em.find(getEntityTypeClass(), id);
        }
    }

    @Override
    public boolean exists(ID id) {
        return doFindOne(id) != null;
    }

    protected void buildWhereClauseOnCriteriaQuery(Root root, CriteriaBuilder cb, CriteriaQuery cq){
        buildWhereClauseOnCriteriaQuery(root,cb,cq,null);
    }

    protected void buildWhereClauseOnCriteriaQuery(Root root, CriteriaBuilder cb, CriteriaQuery cq, Predicate... inputPredicates){
        if(inputPredicates!=null && inputPredicates.length>0){
            cq.where(inputPredicates);
        }
    }

    protected TypedQuery buildTypedQueryWithHints(Node<String> fetchTree, CriteriaQuery cq){
        Map<String, Object> hints =buildHintsByFetchPaths(fetchTree);
        TypedQuery typedQuery = em.createQuery(cq);
        if (hints!=null) {
            for(Map.Entry<String,Object> entry:hints.entrySet())
                typedQuery.setHint(entry.getKey(),entry.getValue());
        }
        return typedQuery;
    }

    protected  Map<String, Object> buildHintsByFetchPaths(Node<String> fetchTree){
        if(fetchTree==null || !fetchTree.hasData()) {
            return null;
        }
        EntityGraph<T> graph = this.em.createEntityGraph(getEntityTypeClass());
        for(Node<String> child: fetchTree.getChildren()) {
            Subgraph<T> subgraph = graph.addSubgraph(child.getData());
            if(!child.hasChildren()){
                continue;
            }
            for(Node<String> subchild: child.getChildren()) {
                fillSubgraphByFetchTree(subgraph, subchild);
            }
        }
        Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("javax.persistence.loadgraph", graph);
        return hints;
    }

    protected void fillSubgraphByFetchTree(Subgraph<T> graph,Node<String> fetchTree){
        Subgraph<T> subgraph =graph.addSubgraph(fetchTree.getData());
        if(!fetchTree.hasChildren()){
            return;
        }
        for(Node<String> node:fetchTree.getChildren()){
            fillSubgraphByFetchTree(subgraph,node);
        }
    }

    public List<T> findAll() {
        return findAll((Node<String>)null);
    }

    @Override
    public List<T> findAll(Node<String> fetchTree) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityTypeClass());
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(root);
        buildWhereClauseOnCriteriaQuery(root,cb,cq);
        TypedQuery<T> typedQuery=buildTypedQueryWithHints(fetchTree,cq);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> findAll(Specification specification) {
        return findAll(specification,(Node<String>)null);
    }

    @Override
    public List<T> findAll(Specification specification,Node<String> fetchTree) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityTypeClass());
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(root);
        buildWhereClauseOnCriteriaQuery(root,cb,cq,specification.toPredicate(root,cq,cb));
        TypedQuery<T> typedQuery=buildTypedQueryWithHints(fetchTree,cq);
        return typedQuery.getResultList();
    }

    @Override
    public Page<T> findAll(Specification specification,Pageable pageable) {
        return findAll(specification,pageable,(Node<String>)null);
    }

    @Override
    public Page<T> findAll(Specification specification,Pageable pageable,Node<String> fetchTree) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityTypeClass());
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(root);
        buildWhereClauseOnCriteriaQuery(root,cb,cq,specification.toPredicate(root,cq,cb));
        if (pageable.getSort() != null) {
            pageable.getSort().forEach(order -> {
                Iterator<String> propertyPartsIterator = Arrays.asList(order.getProperty().split("\\.")).iterator();
                Path path = root.get(propertyPartsIterator.next());
                while (propertyPartsIterator.hasNext()) {
                    path = path.get(propertyPartsIterator.next());
                }
                if (order.isAscending()) {
                    cq.orderBy(cb.asc(path));
                } else {
                    cq.orderBy(cb.desc(path));
                }
            });
        }
        TypedQuery<T> typedQuery=buildTypedQueryWithHints(fetchTree,cq);
        typedQuery.setFirstResult(pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        return new PageImpl<T>(typedQuery.getResultList(), pageable, count(specification));
    }

    @Override
    public List<T> findAll(Sort sort) {
        return findAll(sort,(Node<String>)null);
    }

    @Override
    public List<T> findAll(Sort sort,Node<String> fetchTree) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityTypeClass());
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(root);
        buildWhereClauseOnCriteriaQuery(root,cb,cq);
        sort.forEach(order -> {
            if (order.isAscending()) {
                cq.orderBy(cb.asc(root.get(order.getProperty())));
            } else {
                cq.orderBy(cb.desc(root.get(order.getProperty())));
            }
        });
        TypedQuery<T> typedQuery=buildTypedQueryWithHints(fetchTree,cq);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> findAll(Iterable<ID> iterable) {
        return findAll(iterable,(Node<String>)null);
    }

    @Override
    public List<T> findAll(Iterable<ID> iterable,Node<String> fetchTree) {
        if (!(iterable instanceof Collection)) {
            throw new IllegalArgumentException("Argument 'iterable' in 'SimpleJpaRepository.findAll(Iterable<ID> iterable)' must be instanceof Collection");
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityTypeClass());
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(root);
        buildWhereClauseOnCriteriaQuery(root,cb,cq,root.get("id").in((Collection<ID>) iterable));
        TypedQuery<T> typedQuery=buildTypedQueryWithHints(fetchTree,cq);
        return typedQuery.getResultList();
    }

    @Override
    public Page<T> findAll(Pageable pageable){
        return findAll(pageable,(Node<String>)null);
    }

    @Override
    public Page<T> findAll(Pageable pageable,Node<String> fetchTree) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityTypeClass());
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(root);
        buildWhereClauseOnCriteriaQuery(root,cb,cq);
        if (pageable.getSort() != null) {
            pageable.getSort().forEach(order -> {
                Iterator<String> propertyPartsIterator = Arrays.asList(order.getProperty().split("\\.")).iterator();
                Path path = root.get(propertyPartsIterator.next());
                while (propertyPartsIterator.hasNext()) {
                    path = path.get(propertyPartsIterator.next());
                }
                if (order.isAscending()) {
                    cq.orderBy(cb.asc(path));
                } else {
                    cq.orderBy(cb.desc(path));
                }
            });
        }
        TypedQuery<T> typedQuery=buildTypedQueryWithHints(fetchTree,cq);
        typedQuery.setFirstResult(pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        return new PageImpl<T>(typedQuery.getResultList(), pageable, count());
    }


    @Override
    public long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(cb.count(root));
        buildWhereClauseOnCriteriaQuery(root,cb,cq);
        TypedQuery<Long> typedQuery = em.createQuery(cq);
        return typedQuery.getSingleResult();
    }

    @Override
    public long count(Specification specification) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(getEntityTypeClass());
        cq.select(cb.count(root));
        buildWhereClauseOnCriteriaQuery(root,cb,cq,specification.toPredicate(root,cq,cb));
        TypedQuery<Long> typedQuery = em.createQuery(cq);
        return typedQuery.getSingleResult();
    }

}
