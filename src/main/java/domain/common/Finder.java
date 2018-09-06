package domain.common;

import dataAccess.common.Node;
import domain.common.exception.DataAccessFailedBuisnessException;
import domain.common.exception.EntityWithSuchIdDoesNotExistsBusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;


public interface Finder<T extends DomainObject, ID extends Serializable> {

    T findOne(ID id) throws DataAccessFailedBuisnessException,EntityWithSuchIdDoesNotExistsBusinessException;

    T findOne(ID id, Node<String> initTree) throws DataAccessFailedBuisnessException,EntityWithSuchIdDoesNotExistsBusinessException;

    T findOne(ID id, Node<String> initTree ,Node<String> fetchTree) throws DataAccessFailedBuisnessException,EntityWithSuchIdDoesNotExistsBusinessException;

    boolean exists(ID id);

    List<T> findAll(Iterable<ID> ids, Node<String> initTree) throws DataAccessFailedBuisnessException;

    List<T> findAll(Iterable<ID> ids) throws DataAccessFailedBuisnessException;

    List<T> findAll(Iterable<ID> ids, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException;

    Page<T> findAll(Pageable pageRequest) throws DataAccessFailedBuisnessException;

    Page<T> findAll(Pageable pageRequest, Node<String> initTree) throws DataAccessFailedBuisnessException;

    Page<T> findAll(Pageable pageRequest, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException;

    Page<T> findAll(Specification specification,Pageable pageRequest) throws DataAccessFailedBuisnessException;

    Page<T> findAll(Specification specification,Pageable pageRequest, Node<String> initTree) throws DataAccessFailedBuisnessException;

    Page<T> findAll(Specification specification,Pageable pageRequest, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException;

    List<T> findAll(Sort sort) throws DataAccessFailedBuisnessException;

    List<T> findAll(Sort sort, Node<String> initTree) throws DataAccessFailedBuisnessException;

    List<T> findAll(Sort sort, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException;

    List<T> findAll(Specification specification) throws DataAccessFailedBuisnessException;

    List<T> findAll(Specification specification, Node<String> initTree) throws DataAccessFailedBuisnessException;

    List<T> findAll(Specification specification, Node<String> initTree, Node<String> fetchTree) throws DataAccessFailedBuisnessException;

    List<T> findAll() throws DataAccessFailedBuisnessException;

    List<T> findAll(Node<String> initTree) throws DataAccessFailedBuisnessException;

    List<T> findAll(Node<String> initTree,Node<String> fetchTree) throws DataAccessFailedBuisnessException;


    boolean hasAtLeastOne(Specification specification);

    long count(Specification specification);


}
