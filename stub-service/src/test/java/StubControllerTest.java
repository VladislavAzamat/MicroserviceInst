import aston_org.example.stubservice.StubServiceApplication;
import aston_org.example.stubservice.dto.StubDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = StubServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StubControllerTest {

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate;
    @Autowired
    public StubControllerTest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Test
    public void testGetStubMessage() {
        ResponseEntity<StubDto> response = restTemplate.getForEntity("http://localhost:" + port + "/api/stub", StubDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        StubDto stubDto = response.getBody();
        assertNotNull(stubDto);
        assertNotNull(stubDto.getMessage());
        assertTrue(stubDto.getId() >= 0 && stubDto.getId() <= 100);
    }
}