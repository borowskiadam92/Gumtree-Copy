package com.patryk.marcisz.gumtreecopy.bootstrap;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.LocalizationEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseRolesProvider implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final LocalizationRepository localizationRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String[] args) {
        hashPasswordOfInitialUsers();
        setUpNieruchomosciCategory();
    }

    private void hashPasswordOfInitialUsers() {
        userRepository.findAll().forEach(user ->
                user.setPassword(passwordEncoder.encode(user.getPassword()))
        );
    }

    private void setUpNieruchomosciCategory() {

        CategoryEntity mieszkanieIdomyDoWynajecia = categoryRepository.findBySearchableName("mieszkania-i-domy-do-wynajecia").orElseThrow();
        LocalizationEntity krakow = localizationRepository.findBySearchableName("krakow").orElseThrow();
        UserEntity user = userRepository.findByMailOrNick("admin").orElseThrow();

        OfferEntity savedOffer = offerRepository.save(OfferEntity.builder()
                .title("jagodzianka")
                .price(150)
                .creator(user)
                .category(mieszkanieIdomyDoWynajecia)
                .publishDate(LocalDate.now())
                .content("Wiecie co to jagodzianka? To nie drożdżówka. Myśleliście, że to drożdżówka, a to nie drożdżówka")
                .localization(krakow)
                .build()
        );

        user.setOffers(List.of(savedOffer));
        mieszkanieIdomyDoWynajecia.setOffers(List.of(savedOffer));
    }

}

