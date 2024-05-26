package com.aston_org.mainservice.controller;

import aston_org.example.stubservice.dto.StubDto;
import com.aston_org.mainservice.entity.Example;
import com.aston_org.mainservice.service.ExampleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/examples")
@Tag(name = "Example Management System", description = "Operations pertaining to example management")
public class ExampleController {

    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;

    }

    @Operation(summary = "View a list of available examples", description = "Provides a list of all available examples")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Example> getExamples() {
        return exampleService.getAllExamples();
    }

    @Operation(summary = "Add an example", description = "Adds a new example")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added example"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Example> createExample(@RequestBody Example example) {
        Example savedExample = exampleService.saveExample(example);
        return new ResponseEntity<>(savedExample, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an example by Id", description = "Gets the details of an example by its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved example"),
            @ApiResponse(responseCode = "404", description = "Example not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public Example getExampleById(@PathVariable Long id) {
        return exampleService.getExampleById(id);
    }

    @Operation(summary = "Get stub message", description = "Gets a message from the stub service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved message from stub service"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/stub")
    public StubDto getStubMessage() {
        return exampleService.getStubMessage();
    }

}