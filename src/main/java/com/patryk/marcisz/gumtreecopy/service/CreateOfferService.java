package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.converters.OfferRequestConverter;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import com.patryk.marcisz.gumtreecopy.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOfferService {

    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;
    private final OfferRequestConverter offerRequestConverter;

    public void createOffer(CreateOfferRequest createOfferRequest) {

        OfferEntity savedOffer = offerRepository.save(offerRequestConverter.toDao(createOfferRequest));

    }
}
