package org.example.msenfant.Service;

import java.util.List;

public interface IGeneriqueService<Request, Response> {

    List<Response> getAllEntities();

    Response getEntityById(String id);

    Response createEntity(Request dto);

    Response updateEntity(String id, Request dto);

    void deleteEntity(String id);
}
