package com.beautysalon.api.v1.dto.mapper.base;

public interface AutoMapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
    void autoCopyDto(D source, D destination);
    void autoCopyEntity(E source, E destination);
    void transferDtoEntity(D source, E destination);
    void transferEntityDto(E source, D destination);
}
