package com.aston_org.mainservice.service;

import com.aston_org.mainservice.dto.StubDto;
import com.aston_org.mainservice.entity.Example;
import com.aston_org.mainservice.repository.ExampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExampleService {

    private final ExampleRepository exampleRepository;
    private final RestTemplate restTemplate;

    public ExampleService(ExampleRepository exampleRepository, RestTemplate restTemplate) {
        this.exampleRepository = exampleRepository;
        this.restTemplate = restTemplate;
    }

    public List<Example> getAllExamples() {
        return exampleRepository.findAll();
    }

    public Example saveExample(Example example) {
        return exampleRepository.save(example);
    }

    public Example getExampleById(Long id) {
        return exampleRepository.findById(id).orElse(null);
    }

    public StubDto getStubMessage() {
        return restTemplate.getForObject("http://localhost:8081/api/stub", StubDto.class);

    }
}
