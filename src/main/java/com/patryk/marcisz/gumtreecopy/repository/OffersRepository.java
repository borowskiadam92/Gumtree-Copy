package com.patryk.marcisz.gumtreecopy.repository;

import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface OffersRepository extends JpaRepository<OfferEntity, BigInteger> {

}
