package aston_org.example.stubservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stubs")
public class StubController {

    @Autowired
    private StubService stubService;

    @GetMapping
    public List<Stub> getStubs() {
        return stubService.getAllStubs();
    }

    @PostMapping
    public Stub createStub(@RequestBody Stub stub) {
        return stubService.saveStub(stub);
    }

    @GetMapping("/{id}")
    public Stub getStubById(@PathVariable Long id) {
        return stubService.getStubById(id);
    }
}