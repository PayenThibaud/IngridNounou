package org.example.msenfant.Controller;



import org.example.msenfant.Service.GeneriqueServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GeneriqueController<Request, Response, Service extends GeneriqueServiceImpl<Request, Response, ?>> implements IGeneriqueController<Request, Response> {

    protected final Service service;

    public GeneriqueController(Service service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Response>> getAll() {
        List<Response> entities = service.getAllEntities();
        return ResponseEntity.ok(entities);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable String id) {
        Response entity = service.getEntityById(id);
        return ResponseEntity.ok(entity);
    }

    @Override
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody Request dto) {
        Response entity = service.createEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable String id, @RequestBody Request dto) {
        Response entity = service.updateEntity(id, dto);
        return ResponseEntity.ok(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteEntity(id);
        return ResponseEntity.ok(service.getEntityName() + " avec l'id: " + id + " a etait supprimer");
    }
}