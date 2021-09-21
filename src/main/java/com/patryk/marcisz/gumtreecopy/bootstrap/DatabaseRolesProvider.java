package com.patryk.marcisz.gumtreecopy.bootstrap;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.LocalizationEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import com.patryk.marcisz.gumtreecopy.repository.LocalizationRepository;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        setUpNieruchomosciCategory();
        setUpNieruchomosciCategory();
        setUpNieruchomosciCategory();
        setUpNieruchomosciCategory();
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

        Optional<List<OfferEntity>> userOffers = Optional.ofNullable(user.getOffers());
        if(userOffers.isEmpty()){
            user.setOffers(new ArrayList<>());
        }
        user.getOffers().add(savedOffer);

        Optional<List<OfferEntity>> categoryOffers = Optional.ofNullable(mieszkanieIdomyDoWynajecia.getOffers());
        if(categoryOffers.isEmpty()){
            user.setOffers(new ArrayList<>());
        }
        mieszkanieIdomyDoWynajecia.getOffers().add(savedOffer);
    }

}

