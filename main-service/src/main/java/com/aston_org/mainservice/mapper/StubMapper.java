package com.aston_org.mainservice.mapper;
import aston_org.example.stubservice.dto.StubDto;
import com.aston_org.mainservice.entity.Example;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public abstract class StubMapper {
//    StubMapper INSTANCE = Mappers.getMapper(StubMapper.class);
    @Mapping(source = "message", target = "name")
    public abstract Example stubDtoToExample(StubDto stubDto);

}
