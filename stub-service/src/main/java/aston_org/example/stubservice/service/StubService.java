package aston_org.example.stubservice.service;

import aston_org.example.stubservice.dto.StubDto;
import org.springframework.stereotype.Service;

@Service
public class StubService {
    public StubDto getStubMessage() {
        StubDto stubDto = new StubDto();
        stubDto.setId(1L);
        stubDto.setMessage("This is a stub message");
        return stubDto;
    }
}