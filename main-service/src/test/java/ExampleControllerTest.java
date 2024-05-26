
import aston_org.example.stubservice.dto.StubDto;
import com.aston_org.mainservice.controller.ExampleController;
import com.aston_org.mainservice.entity.Example;
import com.aston_org.mainservice.service.ExampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExampleControllerTest {
    @Mock
    private ExampleService exampleService;
    @InjectMocks
    private ExampleController exampleController;
    private MockMvc mockMvc;
    private Example example1;
    private Example example2;
    private List<Example> exampleList;
    private StubDto stubDto;
    private Instant fixedInstant;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(exampleController).build();

        fixedInstant = Instant.parse("2024-05-24T15:03:00Z");
        example1 = new Example(1L, "Example 1", fixedInstant);
        example2 = new Example(2L, "Example 2", fixedInstant);
        exampleList = Arrays.asList(example1, example2);

        stubDto = new StubDto();
        stubDto.setId(1L);
        stubDto.setMessage("Stub message");
    }

    @Test
    void getExamples_status200AndReturnAllExamples() throws Exception {

        when(exampleService.getAllExamples()).thenReturn(exampleList);

        mockMvc.perform(get("/api/examples").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'Example 1','time':'2024-05-24T15:03:00Z'},{'id':2,'name':'Example 2','time':'2024-05-24T15:03:00Z'}]"));
    }

//    @Test
//    void createExample_status200AndExampleSaved() throws Exception {
//
//        when(exampleService.saveExample(example1)).thenReturn(example1);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String jsonContent = objectMapper.writeValueAsString(example1);
//
//        mockMvc.perform(post("/api/examples")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(content().json(jsonContent));
//    }

    @Test
    void getExampleById_status200AndExampleReceived() throws Exception {

        when(exampleService.getExampleById(1L)).thenReturn(example1);

        mockMvc.perform(get("/api/examples/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Example 1\",\"time\":\"2024-05-24T15:03:00Z\"}"));    }

    @Test
    void getStubMessages_status200AndStubReceived() throws Exception {

        when(exampleService.getStubMessage()).thenReturn(stubDto);

        mockMvc.perform(get("/api/examples/stub").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'message':'Stub message'}"));
    }
}
