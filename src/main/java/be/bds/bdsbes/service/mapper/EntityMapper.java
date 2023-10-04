package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.payload.ChiTietPhongResponse1;

import java.util.List;

public interface EntityMapper <D, E> {
    E toEntity(D dto);
    D toDto(E entity);

    List<E> toEntities(List<D> dtoList);
    List <D> toDtoList(List<E> entityList);

    List<D> toResponseList(List<ChiTietPhongResponse1> entityList);
}