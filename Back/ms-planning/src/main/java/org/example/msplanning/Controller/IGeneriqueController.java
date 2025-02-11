package org.example.msplanning.Controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGeneriqueController<Request, Response> {

    ResponseEntity<List<Response>> getAll();

    ResponseEntity<Response> getById(int id);

    ResponseEntity<Response> create(Request dto);

    ResponseEntity<Response> update(int id, Request dto);

    ResponseEntity<String> delete(int id);
}