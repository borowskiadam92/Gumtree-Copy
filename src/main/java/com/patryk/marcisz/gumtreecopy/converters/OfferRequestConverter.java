package com.patryk.marcisz.gumtreecopy.converters;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import com.patryk.marcisz.gumtreecopy.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Transactional
public class OfferRequestConverter implements ConvertWithPersist<OfferEntity, CreateOfferRequest> {

    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;
    private final OfferRepository offerRepository;

    @Override
    public CreateOfferRequest toDto(OfferEntity offerEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OfferEntity toDao(CreateOfferRequest request) {
        CategoryEntity categoryEntity = getCategory(request);
        UserEntity userEntity = getCreator();

        OfferEntity newOffer = OfferEntity.builder()
                .title(request.getTitle())
                .category(getCategory(request))
                .publishDate(LocalDate.now())
                .creator(getCreator())
                .content(request.getContent())
                .price(request.getPrice())
                .build();

        newOffer = offerRepository.save(newOffer);

        List<OfferEntity> categoryOffers = categoryEntity.getOffers();
        if (Objects.isNull(categoryOffers)) {
            categoryEntity.setOffers(List.of(newOffer));
        }

        List<OfferEntity> userOffers = userEntity.getOffers();
        if (Objects.isNull(userOffers)) {
            categoryEntity.setOffers(List.of(newOffer));
        }

        return newOffer;
    }

    private CategoryEntity getCategory(CreateOfferRequest request) {
        return categoryRepository.findById(request.getCategoryId()).orElseThrow();
    }

    private UserEntity getCreator() {
        return usersRepository.findByMailOrNick(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
    }

    @Override
    public CreateOfferRequest convertAndPersist(OfferEntity offerEntity) {
        return null;
    }
}
