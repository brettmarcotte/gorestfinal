package com.example.gorestfinal.repositories;

public interface TodoRepository {
    <ToDo> Iterable<ToDo> findAll();
}
