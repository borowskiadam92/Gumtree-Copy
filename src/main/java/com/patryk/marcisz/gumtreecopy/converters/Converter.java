package com.patryk.marcisz.gumtreecopy.converters;

public interface Converter <DAO, DTO>{
    DTO toDto(DAO dao);

    DAO toDao(DTO dto);

}
