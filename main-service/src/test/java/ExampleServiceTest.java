
import aston_org.example.stubservice.dto.StubDto;
import com.aston_org.mainservice.entity.Example;
import com.aston_org.mainservice.mapper.StubMapper;
import com.aston_org.mainservice.repository.ExampleRepository;
import com.aston_org.mainservice.service.ExampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExampleServiceTest {
    @Mock
    private ExampleRepository exampleRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ExampleService exampleService;
    private Example example1;
    private List<Example> exampleList;
    private StubDto stubDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        example1 = new Example(1L, "Example 1", Instant.now());
        Example example2 = new Example(2L, "Example 2", Instant.now());
        exampleList = Arrays.asList(example1, example2);

        stubDto = new StubDto();
        stubDto.setId(1L);
        stubDto.setMessage("Stub message");
    }

    @Test
    void getAllExamples_returnAllExamplesOrNull() {


        when(exampleRepository.findAll()).thenReturn(exampleList);

        List<Example> result = exampleService.getAllExamples();
        assertNotNull(exampleList);
        assertEquals(2, result.size());
        assertEquals("Example 1", result.get(0).getName());
    }

    @Test
    void saveExample_newExample_exampleSaved() {

        when(exampleRepository.save(any(Example.class))).thenReturn(example1);

        Example result = exampleService.saveExample(example1);
        assertNotNull(result);
        assertEquals("Example 1", result.getName());
    }

    @Test
    void getExampleById_putId_exampleReceived() {

        when(exampleRepository.findById(1L)).thenReturn(Optional.of(example1));

        Example result = exampleService.getExampleById(1L);
        assertNotNull(result);
        assertEquals("Example 1", result.getName());
    }

    @Test
    void getStubMessage_initRestTemplate_stubMessageReceived() {

        when(restTemplate.getForObject(anyString(), eq(StubDto.class))).thenReturn(stubDto);

        StubDto result = exampleService.getStubMessage();
        assertNotNull(result);
        assertEquals("Stub message", result.getMessage());
    }

}
