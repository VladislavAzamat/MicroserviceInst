package com.aston_org.mainservice.mapper;
import aston_org.example.stubservice.dto.StubDto;
import com.aston_org.mainservice.entity.Example;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
@Component
public abstract class StubMapper {

    @Mapping(source = "message", target = "name")
    @Mapping(target = "id", constant = "1L" )
    public abstract Example stubDtoToExample(StubDto stubDto);

    @AfterMapping
    protected void setTimestamp(@MappingTarget Example example) {
        example.setTime(Instant.now());
    }


}
