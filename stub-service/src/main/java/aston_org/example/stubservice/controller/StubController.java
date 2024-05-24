package aston_org.example.stubservice.controller;

//import aston_org.example.stubservice.dto.StubDto;
import aston_org.example.stubservice.dto.StubDto;
import aston_org.example.stubservice.service.StubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/stub")
@Tag(name = "Stub Service", description = "Stub service operations")
public class StubController {

    private final StubService stubService;

    public StubController(StubService stubService) {
        this.stubService = stubService;
    }

    @Operation(summary = "Stub endpoint", description = "Returns a stub message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved message"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public StubDto getStubMessage() {
        return stubService.getStubMessage();
    }
//    @GetMapping
//    public String getStubMessage() {
//        return stubService.getStubMessage();
//    }

}