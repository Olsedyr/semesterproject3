package org.example.beerMachine.service;

import org.example.beerMachine.model.Request;
import org.example.beerMachine.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository RequestRepository;

    @Autowired
    public RequestService(RequestRepository RequestRepository) {
        this.RequestRepository = RequestRepository;
    }

    public List<Request> getRequest() {
        return RequestRepository.findAll();
    }


}

