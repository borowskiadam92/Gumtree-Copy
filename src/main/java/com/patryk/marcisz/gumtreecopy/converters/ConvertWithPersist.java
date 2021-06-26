package com.patryk.marcisz.gumtreecopy.converters;

public interface ConvertWithPersist<DAO, DTO> extends Converter<DAO, DTO> {

    DAO convertAndPersist(DTO dto);

}
