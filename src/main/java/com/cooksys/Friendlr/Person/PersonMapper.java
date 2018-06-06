package com.cooksys.Friendlr.Person;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PersonMapper {

	PersonDto toDto(Person person);
	Person fromDto(PersonDto personDto);
}
