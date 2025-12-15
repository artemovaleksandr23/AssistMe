package com.otache.AssistMe.Repositories.impl;

import com.otache.AssistMe.Models.Message.Message;
import com.otache.AssistMe.Models.Role.Role;
import com.otache.AssistMe.Repositories.MessageRepository;
import com.otache.AssistMe.Repositories.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgresMessageRepository implements MessageRepository {
    private final DataSource dataSource;

    public PostgresMessageRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int save(Message message) throws SQLException {
        String sql = "INSERT INTO messages (user_id, title, body, status, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            LocalDateTime now = LocalDateTime.now();
            ps.setInt(1, message.getUserId());
            ps.setString(2, message.getTitle());
            ps.setString(3, message.getBody());
            ps.setString(4, message.getStatus());
            ps.setTimestamp(5, Timestamp.valueOf(now));
            ps.setTimestamp(6, Timestamp.valueOf(now));

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating message failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    message.setId(id); // если нужно сохранить id в объекте
                    return id;
                } else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Message> findAll() throws SQLException {
        String sql = "SELECT * FROM messages ORDER BY created_at DESC";
        List<Message> messages = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("body"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        }
        return messages;
    }

    @Override
    public Optional<Message> findById(int id) throws SQLException {
        String sql = "SELECT * FROM messages WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Message(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("body"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Message message) throws SQLException {
        String sql = "UPDATE messages SET title=?, body=?, status=?, updated_at=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, message.getTitle());
            ps.setString(2, message.getBody());
            ps.setString(3, message.getStatus());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(5, message.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM messages WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
