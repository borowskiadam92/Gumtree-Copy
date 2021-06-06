package com.patryk.marcisz.gumtreecopy.service;

import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import com.patryk.marcisz.gumtreecopy.repository.CategoriesRepository;
import com.patryk.marcisz.gumtreecopy.repository.OffersRepository;
import com.patryk.marcisz.gumtreecopy.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CreateOfferService {

    private final OffersRepository offersRepository;
    private final CategoriesRepository categoriesRepository;
    private final UsersRepository usersRepository;

    public void createOffer(CreateOfferRequest createOfferRequest, String username) {
        Function<CreateOfferRequest, OfferEntity> offerFromRequestToDto = (CreateOfferRequest request) -> OfferEntity.builder()
                .title(request.getTitle())
                .category(categoriesRepository.findById(createOfferRequest.getCategoryId()).orElseThrow())
                .creator(usersRepository.findByMailOrNick(username).orElseThrow())
                .content(request.getContent())
                .publishDate(LocalDate.now())
                .price(request.getPrice())
                .build();

        offersRepository.save(offerFromRequestToDto.apply(createOfferRequest));
    }
}
