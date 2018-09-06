package dataAccess.common;

import dataAccess.common.exception.DataAccessException;
import dataAccess.common.exception.EntityWithSuchIdDoesNotExistsDataAccessException;
import domain.common.exception.EntityWithSuchIdIsSoftDeletedBusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Администратор on 29.10.2017.
 */
public interface Repository<T, ID extends Serializable> {

    <S extends T> S save(S s);

    <S extends T> List<S> save(Iterable<S> iterable);

    void flush();

    <S extends T> S saveAndFlush(S s);

    void deleteAndFlush(ID id) throws EntityWithSuchIdDoesNotExistsDataAccessException;

    void deleteAndFlush(T t);

    void deleteAndFlush(Iterable<? extends T> iterable);

    void deleteAll();

    void deleteInBatch(Iterable<T> iterable);

    void deleteAllInBatch();

    void clearPersistenceContext();

    T findOne(ID id) throws DataAccessException, EntityWithSuchIdDoesNotExistsDataAccessException;

    T findOne(ID id,Node<String> fetchTree) throws DataAccessException, EntityWithSuchIdDoesNotExistsDataAccessException;

    List<T> findAll();

    List<T> findAll(Node<String> fetchTree);

    List<T> findAll(Specification specification);

    List<T> findAll(Specification specification,Node<String> fetchTree);

    List<T> findAll(Sort sort);

    List<T> findAll(Sort sort,Node<String> fetchTree);

    List<T> findAll(Iterable<ID> iterable);

    List<T> findAll(Iterable<ID> iterable,Node<String> fetchTree);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Pageable pageable,Node<String> fetchTree);

    Page<T> findAll(Specification specification,Pageable pageable);

    Page<T> findAll(Specification specification,Pageable pageable,Node<String> fetchTree);

    boolean exists(ID id);

    long count();

    long count(Specification specification);
}
