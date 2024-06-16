package aston_org.example.stubservice.service;

import java.util.Random;

import aston_org.example.stubservice.dto.StubDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StubService {
    private static final String TOPIC = "example_topic";

    @Autowired
    private KafkaTemplate<String, StubDto> kafkaTemplate;

    public StubDto getStubMessage() {

        Random random = new Random();
        long id = random.nextInt(101);

        StubDto stubDto = new StubDto();
        stubDto.setId(id);
        stubDto.setMessage("This is a STUB!!!!!");

        kafkaTemplate.send(TOPIC, stubDto);
        return stubDto;
    }

}