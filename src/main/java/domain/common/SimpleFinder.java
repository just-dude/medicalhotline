package domain.common;

import common.argumentAssert.Assert;
import dataAccess.common.Node;
import dataAccess.common.Repository;
import dataAccess.common.exception.EntityWithSuchIdDoesNotExistsDataAccessException;
import dataAccess.common.exception.EntityWithSuchIdIsSoftDeletedDataAccessException;
import domain.common.exception.DataAccessFailedBuisnessException;
import domain.common.exception.EntityWithSuchIdDoesNotExistsBusinessException;
import domain.common.exception.EntityWithSuchIdIsSoftDeletedBusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public class SimpleFinder<T extends DomainObject, ID extends Serializable> implements Finder<T, ID> {

    protected Repository<T, ID> repository;

    protected Node<String> defaultFetchTree;

    public SimpleFinder(Repository repository) {
        this.repository = repository;
    }

    public SimpleFinder(Repository repository,Node<String> defaultFetchTree) {
        this.repository = repository;
        this.defaultFetchTree=defaultFetchTree;
    }

    @Override
    public T findOne(ID id) throws DataAccessFailedBuisnessException {
        return findOne(id, null,null);
    }

    @Override
    public T findOne(ID id, Node<String> initTree) throws DataAccessFailedBuisnessException {
        return findOne(id, initTree,null);
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(ID id, Node<String> initTree,Node<String> fetchTree) throws DataAccessFailedBuisnessException,EntityWithSuchIdDoesNotExistsBusinessException,EntityWithSuchIdIsSoftDeletedDataAccessException {
        Assert.notNull(id,"id");
        try {
            T result=repository.findOne(id,mergeFetchTrees(fetchTree,defaultFetchTree));
            result.initializeProperties(initTree);
            return result;
        } catch (EntityWithSuchIdDoesNotExistsDataAccessException e) {
            throw new EntityWithSuchIdDoesNotExistsBusinessException(id,e);
        } catch (EntityWithSuchIdIsSoftDeletedDataAccessException e) {
            throw new EntityWithSuchIdIsSoftDeletedBusinessException(id,e);
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findOneOrThrowException(Id id)", id);
        }
    }

    @Override
    public List<T> findAll(Iterable<ID> ids) throws DataAccessFailedBuisnessException {
        return findAll(ids,null);
    }

    @Override
    public List<T> findAll(Iterable<ID> ids, Node<String> initTree) throws DataAccessFailedBuisnessException {
        return findAll(ids,initTree,null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(Iterable<ID> ids, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException {
        Assert.notNull(ids,"ids");
        Assert.notContainsNullElements((Collection<ID>) ids,"ids");
        try {
            List<T> result=repository.findAll(ids,mergeFetchTrees(fetchTree,defaultFetchTree));
            if (initTree!=null){
                for(T element: result){
                    element.initializeProperties(initTree);
                }
            }
            return result;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findAll(Iterable<ID> ids)", StringUtils.join(ids,","));
        }
    }

    @Override
    public Page<T> findAll(Pageable pageable) throws DataAccessFailedBuisnessException {
        return findAll(pageable, null, null);
    }

    @Override
    public Page<T> findAll(Pageable pageable, Node<String> initTree) throws DataAccessFailedBuisnessException {
        return findAll(pageable, initTree, null);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Pageable pageable, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException {
        Assert.notNull(pageable,"pageable");
        try {
            Page<T> result=repository.findAll(pageable,mergeFetchTrees(fetchTree,defaultFetchTree));
            if(initTree!=null) {
                for (T element : result.getContent()) {
                    element.initializeProperties(initTree);
                }
            }
            return result;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findAll(Pageable pageable)", pageable);
        }
    }

    @Override
    public Page<T> findAll(Specification specification,Pageable pageable) throws DataAccessFailedBuisnessException {
        return findAll(specification,pageable, null,null);
    }

    @Override
    public Page<T> findAll(Specification specification,Pageable pageable, Node<String> initTree) throws DataAccessFailedBuisnessException {
        return findAll(specification,pageable, initTree,null);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Specification specification,Pageable pageable, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException {
        Assert.notNull(pageable,"pageable");
        Assert.notNull(specification,"specification");
        try {
            Page<T> result=repository.findAll(specification,pageable,mergeFetchTrees(fetchTree,defaultFetchTree));
            if(initTree!=null) {
                for (T element : result.getContent()) {
                    element.initializeProperties(initTree);
                }
            }
            return result;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findAll(Specification specification,Pageable pageable)",specification,pageable);
        }
    }

    @Override
    public List<T> findAll(Sort sort) throws DataAccessFailedBuisnessException {
        return findAll(sort, null);
    }

    @Override
    public List<T> findAll(Sort sort, Node<String> initTree) throws DataAccessFailedBuisnessException {
        return findAll(sort, initTree,null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(Sort sort, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException {
        Assert.notNull(sort,"sort");
        try {
            List<T> result= repository.findAll(sort,mergeFetchTrees(fetchTree,defaultFetchTree));
            if(initTree!=null) {
                for (T element : result) {
                    element.initializeProperties(initTree);
                }
            }
            return result;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findAll(Sort sort)", sort);
        }
    }

    @Override
    public List<T> findAll(Specification specification) throws DataAccessFailedBuisnessException {
        return findAll(specification,(Node<String>)null);
    }

    @Override
    public List<T> findAll(Specification specification, Node<String> initTree) throws DataAccessFailedBuisnessException {
        return findAll(specification,initTree,(Node<String>)null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(Specification specification, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException {
        try {
            List<T> result= repository.findAll(specification,mergeFetchTrees(fetchTree,defaultFetchTree));
            if(initTree!=null) {
                for (T element : result) {
                    element.initializeProperties(initTree);
                }
            }
            return result;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findAll()");
        }
    }

    @Override
    public List<T> findAll() throws DataAccessFailedBuisnessException {
        return findAll((Node<String>)null);
    }

    @Override
    public List<T> findAll(Node<String> initTree) throws DataAccessFailedBuisnessException {
        return findAll(initTree,(Node<String>)null);
    }

    @Override
    public List<T> findAll(Node<String> initTree,Node<String> fetchTree) throws DataAccessFailedBuisnessException {
        try {
            List<T> result=null;
            result= repository.findAll(mergeFetchTrees(fetchTree,defaultFetchTree));
            if(initTree!=null) {
                for (T element : result) {
                    element.initializeProperties(initTree);
                }
            }
            return result;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".findAll()");
        }
    }

    @Override
    public boolean exists(ID id) {
        Assert.notNull(id,"id");
        try {
            return repository.exists(id);
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".exists(id)", StringUtils.join(id,","));
        }
    }

    @Override
    public boolean hasAtLeastOne(Specification specification) throws DataAccessFailedBuisnessException {
        try {
            return repository.count(specification)>0;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".hasAtLeastOne(specification)");
        }
    }

    @Override
    public long count(Specification specification) {
        try {
            return repository.count(specification);
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on read from data storage", e, this.getClass().getSimpleName() + ".count(Specification specification)");
        }
    }

    protected Node<String> mergeFetchTrees(Node<String> fetchTree1, Node<String> fetchTree2){
        if(fetchTree1==null){
            return fetchTree2;
        }
        if(fetchTree2==null){
            return fetchTree1;
        }
        return Node.rootNode().addChildren(fetchTree1.getChildren()).addChildren(fetchTree2.getChildren());
    }

}
