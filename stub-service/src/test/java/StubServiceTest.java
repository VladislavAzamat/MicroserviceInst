import aston_org.example.stubservice.dto.StubDto;
import aston_org.example.stubservice.service.StubService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StubServiceTest {

    private final StubService stubService = new StubService();

    @Test
    public void testGetStubMessage() {
        StubDto stubDto = stubService.getStubMessage();

        assertNotNull(stubDto);
        assertNotNull(stubDto.getMessage());
        assertTrue(stubDto.getId() >= 0 && stubDto.getId() <= 100);
    }
}