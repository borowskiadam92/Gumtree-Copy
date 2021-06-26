package com.patryk.marcisz.gumtreecopy.converters;

import com.patryk.marcisz.gumtreecopy.exceptions.AppErrorMessage;
import com.patryk.marcisz.gumtreecopy.exceptions.GumtreeCopyApiException;
import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import com.patryk.marcisz.gumtreecopy.security.GumtreeCopyUserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Transactional
public class OfferRequestConverter implements ConvertWithPersist<OfferEntity, CreateOfferRequest> {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final GumtreeCopyUserContext userContext;

    @Override
    public OfferEntity convertAndPersist(CreateOfferRequest offerRequest) {
        return offerRepository.save(toDao(offerRequest));
    }

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
                .category(categoryEntity)
                .creator(userEntity)
                .publishDate(LocalDate.now())
                .content(request.getContent())
                .price(request.getPrice())
                .build();

        List<OfferEntity> categoryOffers = newOffer.getCategory().getOffers();
        if (Objects.isNull(categoryOffers)) {
            categoryOffers = new ArrayList<>();
            categoryEntity.setOffers(categoryOffers);
        }
        categoryOffers.add(newOffer);

        List<OfferEntity> userOffers = newOffer.getCreator().getOffers();
        if (Objects.isNull(userOffers)) {
            userOffers = new ArrayList<>();
            userEntity.setOffers(userOffers);
        }
        userOffers.add(newOffer);

        return newOffer;
    }

    private CategoryEntity getCategory(CreateOfferRequest request) {
        return categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new GumtreeCopyApiException(AppErrorMessage.MISSING_CATEGORY, String.valueOf(request.getCategoryId())));
    }

    private UserEntity getCreator() {
        return userRepository.findByMailOrNick(userContext.getCurrentUserName())
                .orElseThrow();
    }
}
