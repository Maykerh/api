package com.zup.api.dto;

import org.modelmapper.ModelMapper;

public interface DTOInterface {
    public Object getEntity();

    // public static DTOInterface instanceFromEntity(Object entity) {
    //     ModelMapper modelMapper = new ModelMapper();

    //     return modelMapper.map(entity, DTOInterface.class);
    // }
}
