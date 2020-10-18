package com.zup.api.utils;

import org.modelmapper.ModelMapper;

public class DTOMapper {
    public static final ModelMapper modelMapper = new ModelMapper();

    public static Object toEntity(Object DTO, Class<? extends Object> entity ) {
        return modelMapper.map(DTO, entity);
    }

    public static Object fromEntity(Object entity, Class<? extends Object> DTO) {
        return modelMapper.map(entity, DTO);
    }
}
