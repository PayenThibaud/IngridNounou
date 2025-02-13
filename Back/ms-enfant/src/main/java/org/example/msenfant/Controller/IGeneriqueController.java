package org.example.msenfant.Controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGeneriqueController<Request, Response> {

    ResponseEntity<List<Response>> getAll();

    ResponseEntity<Response> getById(String id);

    ResponseEntity<Response> create(Request dto);

    ResponseEntity<Response> update(String id, Request dto);

    ResponseEntity<String> delete(String id);
}