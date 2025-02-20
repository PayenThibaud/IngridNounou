package org.example.msgateway.Controller.Generique;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.msgateway.tools.RestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

public abstract class GeneriqueController<Request, Response> {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected abstract String getBaseUrl();

    protected abstract String getEntityName();

    protected abstract Class<Response[]> getArrayResponseType();

    protected abstract Class<Response> getSingleResponseType();

    @GetMapping
    public ResponseEntity<List<Response>> getAll() {
        RestClient<Response[]> restClient = new RestClient<>(getBaseUrl());
        List<Response> responses = Arrays.stream(restClient.getRequest(getArrayResponseType())).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable int id) {
        RestClient<Response> restClient = new RestClient<>(getBaseUrl() + "/" + id);
        Response response = restClient.getRequest(getSingleResponseType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody Request dto) throws JsonProcessingException {
        RestClient<Response> restClient = new RestClient<>(getBaseUrl());
        Response response = restClient.postRequest(objectMapper.writeValueAsString(dto), getSingleResponseType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable int id, @RequestBody Request dto) throws JsonProcessingException {
        RestClient<Response> restClient = new RestClient<>(getBaseUrl() + "/" + id);
        Response response = restClient.putRequest(objectMapper.writeValueAsString(dto), getSingleResponseType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        RestClient<Void> restClient = new RestClient<>(getBaseUrl() + "/" + id);
        restClient.deleteRequest();
        return new ResponseEntity<>( getEntityName() + " avec l' ID : " + id + " est supprimer.", HttpStatus.OK);
    }
}
