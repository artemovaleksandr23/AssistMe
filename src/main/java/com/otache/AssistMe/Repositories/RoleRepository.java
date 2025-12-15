package com.otache.AssistMe.Repositories;

import com.otache.AssistMe.Models.Role.Role;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role> {
    Optional<Role> findByName(String name) throws SQLException;

    Optional<Role> findById(int id) throws SQLException;

    List<Role> findAll() throws SQLException;
}
