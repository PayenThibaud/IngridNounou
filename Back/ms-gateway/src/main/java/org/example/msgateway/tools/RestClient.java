package org.example.msgateway.tools;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RestClient<T> {
    private String urlApi ;
    private RestTemplate template;
    private HttpHeaders httpHeaders;

    public RestClient(String urlApi) {
        this.urlApi = urlApi;
        template = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "*/*");
        httpHeaders.add("content-type", "application/json");

    }

    public T postRequest(String json, Class<T> type) {
        HttpEntity<String> requestEntity = new HttpEntity<>(json,httpHeaders);
        ResponseEntity<T> response = template.exchange(urlApi, HttpMethod.POST, requestEntity, type);
        if(response.hasBody()) {
            return response.getBody();
        }
        return null;
    }
    public T getRequest( Class<T> type) {
        HttpEntity<String> requestEntity = new HttpEntity<>("",httpHeaders);
        ResponseEntity<T> response = template.exchange(urlApi, HttpMethod.GET, requestEntity, type);
        if(response.hasBody()) {
            return response.getBody();
        }
        return null;
    }

    public T putRequest(String json, Class<T> type) {
        HttpEntity<String> requestEntity = new HttpEntity<>(json, httpHeaders);
        ResponseEntity<T> response = template.exchange(urlApi, HttpMethod.PUT, requestEntity, type);
        return response.hasBody() ? response.getBody() : null;
    }

    public void deleteRequest() {
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        template.exchange(urlApi, HttpMethod.DELETE, requestEntity, Void.class);
    }




    public boolean testToken(String token, Class<T> type) {
        httpHeaders.clear();
        httpHeaders.add("Accept", "*/*");
        httpHeaders.add("Content-Type", "application/json");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String tokenVerif = (String) session.getAttribute("token");

        if (tokenVerif == null) {
            System.out.println("Le token de la session est null.");
            return false;
        }

        httpHeaders.add("Authorization", "Bearer " + tokenVerif);


        HttpEntity<String> requestEntity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<T> response = template.exchange(urlApi, HttpMethod.GET, requestEntity, type);

        return response.hasBody() && response.getStatusCode().is2xxSuccessful();
    }

}