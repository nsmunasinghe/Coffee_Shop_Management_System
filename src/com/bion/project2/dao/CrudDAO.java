package com.bion.project2.dao;

import java.util.List;

public interface CrudDAO<T,ID> extends SuperDAO {
    boolean save(T t) throws Exception;

    boolean update(T t) throws Exception;

    T find(ID id) throws Exception;

    List<T> findAll() throws Exception;

    boolean delete(ID id) throws Exception;
}
