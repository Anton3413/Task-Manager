package com.anton3413.taskmanager.mapper;

public interface Mapper <DTO, E> {

     DTO toDto(E entity);

     E toEntity(DTO dto);
}
