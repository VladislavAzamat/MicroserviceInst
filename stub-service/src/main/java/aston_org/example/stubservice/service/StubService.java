package aston_org.example.stubservice.service;
import java.util.Random;
import aston_org.example.stubservice.dto.StubDto;
import org.springframework.stereotype.Service;

@Service
public class StubService {
    public StubDto getStubMessage() {

        Random random = new Random();
        long id = random.nextInt(101);

        StubDto stubDto = new StubDto();
        stubDto.setId(id);
        stubDto.setMessage("This is a STUB!!!!! ");
        return stubDto;
    }

}