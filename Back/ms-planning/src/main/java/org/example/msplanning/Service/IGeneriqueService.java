package org.example.msplanning.Service;

import java.util.List;

public interface IGeneriqueService<Request, Response> {

    List<Response> getAllEntities();

    Response getEntityById(int id);

    Response createEntity(Request dto);

    Response updateEntity(int id, Request dto);

    void deleteEntity(int id);
}
