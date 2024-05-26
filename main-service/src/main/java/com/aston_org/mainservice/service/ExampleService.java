package com.aston_org.mainservice.service;

//import com.aston_org.mainservice.dto.StubDto;
import aston_org.example.stubservice.dto.StubDto;
import com.aston_org.mainservice.entity.Example;
import com.aston_org.mainservice.exception.InvalidDataException;
import com.aston_org.mainservice.mapper.StubMapper;
import com.aston_org.mainservice.repository.ExampleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExampleService {

    private final StubMapper stubMapper;
    private final ExampleRepository exampleRepository;
    private final RestTemplate restTemplate;

    public ExampleService(ExampleRepository exampleRepository, RestTemplate restTemplate, StubMapper stubMapper) {
        this.exampleRepository = exampleRepository;
        this.restTemplate = restTemplate;
        this.stubMapper = stubMapper;
    }

    public List<Example> getAllExamples() {
        return exampleRepository.findAll();
    }

    public Example saveExample(Example example) {
        if (!isValid(example)) {
            throw new InvalidDataException("Invalid data provided");
        }
        return exampleRepository.save(example);
    }
    public Example getExampleById(Long id) {
        return exampleRepository.findById(id).orElseThrow(() -> new RuntimeException("No entity wit specified id!!!"));
    }

    @Scheduled(fixedRate = 2000)
    public  StubDto getStubMessage() {
        StubDto incomingStub = restTemplate.getForObject("http://localhost:8081/api/stub", StubDto.class);
        exampleRepository.save(stubMapper.stubDtoToExample(incomingStub));
        return incomingStub;
    }
    private boolean isValid(Example example) {
        return example != null && example.getName() != null && !example.getName().isEmpty() && example.getTime() != null;
    }
}
