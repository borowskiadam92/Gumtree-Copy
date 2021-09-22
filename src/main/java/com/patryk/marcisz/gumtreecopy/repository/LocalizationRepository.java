package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.LocalizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LocalizationRepository extends JpaRepository<LocalizationEntity, Long> {

    @Query("SELECT cat FROM LocalizationEntity cat WHERE cat.parent.searchableName like 'wszystkie-lokalizacje'")
    List<LocalizationEntity> findAllChildrenOfMainCategory();

    Optional<LocalizationEntity> findBySearchableName(String searchableName);

}
