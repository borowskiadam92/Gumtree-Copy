package com.patryk.marcisz.gumtreecopy.converters;

public interface ConvertWithPersist<DAO, DTO> extends Converter<DAO, DTO> {

    DTO convertAndPersist(DAO dao);

}
