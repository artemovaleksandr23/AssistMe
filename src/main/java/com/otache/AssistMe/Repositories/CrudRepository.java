package com.otache.AssistMe.Repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    int save(T entity) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean update(T entity) throws SQLException;
}
