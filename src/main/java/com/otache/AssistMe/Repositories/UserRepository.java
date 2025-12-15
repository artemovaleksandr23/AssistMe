package com.otache.AssistMe.Repositories;

import com.otache.AssistMe.Models.Role.Role;
import com.otache.AssistMe.Models.User.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> findByUsername(String username) throws SQLException;

    Optional<User> findByEmail(String email) throws SQLException;

    boolean addUserRole(int userId, int roleId) throws SQLException;

    public int getLastInsertId() throws SQLException;

    public List<Role> getUserRoles(int userId) throws SQLException;
}
