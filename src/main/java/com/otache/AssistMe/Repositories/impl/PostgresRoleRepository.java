package com.otache.AssistMe.Repositories.impl;

import com.otache.AssistMe.Models.Role.Role;
import com.otache.AssistMe.Repositories.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgresRoleRepository implements RoleRepository {
    private final DataSource dataSource;

    public PostgresRoleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Role> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM roles WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Role(rs.getInt("id"), rs.getString("name")));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> findById(int id) throws SQLException {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Role(rs.getInt("id"), rs.getString("name")));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() throws SQLException {
        String sql = "SELECT * FROM roles";
        List<Role> roles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                roles.add(new Role(rs.getInt("id"), rs.getString("name")));
            }
        }
        return roles;
    }

    @Override
    public int save(Role entity) throws SQLException {
        return 0; // Roles are predefined; no need to implement save.
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Role entity) throws SQLException {
        String sql = "UPDATE roles SET name = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());
            return ps.executeUpdate() > 0;
        }
    }
}
