
import aston_org.example.stubservice.dto.StubDto;
import com.aston_org.mainservice.MainServiceApplication;
import com.aston_org.mainservice.entity.Example;
import com.aston_org.mainservice.repository.ExampleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = MainServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExampleControllerIntegrationTest {

    private final TestRestTemplate restTemplate;
    private final ExampleRepository exampleRepository;
    private Example example1ForGettingId;
    private Instant fixedInstant;


    @Autowired
    public ExampleControllerIntegrationTest(TestRestTemplate restTemplate, ExampleRepository exampleRepository) {
        this.restTemplate = restTemplate;
        this.exampleRepository = exampleRepository;
    }


    @BeforeEach
    void setUp() {
        exampleRepository.deleteAll();

        fixedInstant = Instant.parse("2024-05-24T15:03:00Z");
        Example example1 = new Example(1L, "Example 1", fixedInstant);
        Example example2 = new Example(2L, "Example 2", fixedInstant);

        example1ForGettingId = exampleRepository.save(example1);
        exampleRepository.save(example2);

    }


    @Test
    void getExamples_return200AndListOfExamples() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/examples", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void createExample_return201AndNewExample() {
        Example example = new Example(3L, "Example 3", fixedInstant);
        ResponseEntity<Example> response = restTemplate.postForEntity("/api/examples", example, Example.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Example 3", response.getBody().getName());
        assertEquals(fixedInstant, response.getBody().getTime());
    }

    @Test
    void createExampleInvalid_return400AndMessageFromExceptionHandler() {
        Example example = new Example(0, "", null);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/examples", example, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getExampleById_return201AndNewExample() {
        ResponseEntity<Example> response = restTemplate.getForEntity("/api/examples/" + example1ForGettingId.getId(), Example.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Example 1", response.getBody().getName());
        assertEquals(fixedInstant, response.getBody().getTime());
    }

    @Test
    void getExampleByInvalidId_return404AndMessageFromExceptionHandler() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/examples/99", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getStubMessage_return200AndStubMessage() {
        ResponseEntity<StubDto> response = restTemplate.getForEntity("/api/examples/stub", StubDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("This is a STUB!!!!!", response.getBody().getMessage());
    }

    @Test
    void concurrentRequests_return201AndListFromFindAllMethod() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    Example example = new Example(0, "Concurrent Example" + finalI, fixedInstant);
                    ResponseEntity<Example> response = restTemplate.postForEntity("/api/examples", example, Example.class);
                    assertEquals(HttpStatus.CREATED, response.getStatusCode());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        List<Example> examples = exampleRepository.findAll();
        assertEquals(12, examples.size());
    }
}