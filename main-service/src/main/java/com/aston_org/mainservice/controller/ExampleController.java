package com.example.mainservice.controller;

import com.example.mainservice.entity.Example;
import com.example.mainservice.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examples")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping
    public List<Example> getExamples() {
        return exampleService.getAllExamples();
    }

    @PostMapping
    public Example createExample(@RequestBody Example example) {
        return exampleService.saveExample(example);
    }

    @GetMapping("/{id}")
    public Example getExampleById(@PathVariable Long id) {
        return exampleService.getExampleById(id);
    }
}