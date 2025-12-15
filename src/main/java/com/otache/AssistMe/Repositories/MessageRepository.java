package com.otache.AssistMe.Repositories;

import com.otache.AssistMe.Models.Message.Message;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message> {

    List<Message> findAll() throws SQLException;

    Optional<Message> findById(int id) throws SQLException;

    int save(Message message) throws SQLException;

    boolean update(Message message) throws SQLException;

    boolean delete(int id) throws SQLException;
}