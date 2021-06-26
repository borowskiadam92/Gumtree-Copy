package com.patryk.marcisz.gumtreecopy.bootstrap;

import com.patryk.marcisz.gumtreecopy.model.dao.AuthorityEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.repository.AuthorityRepository;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import com.patryk.marcisz.gumtreecopy.repository.OfferRepository;
import com.patryk.marcisz.gumtreecopy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseRolesProvider implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final CategoryRepository categoryRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String[] args) {
        createDefaultUsers();
        setUpNieruchomosciCategory();
        setUpDomIogrodCategory();
    }

    private void createDefaultUsers() {
        AuthorityEntity adminRole = new AuthorityEntity();
        adminRole.setName("ROLE_ADMIN");
        adminRole = authorityRepository.save(adminRole);

        AuthorityEntity userRole = new AuthorityEntity();
        userRole.setName("ROLE_USER");
        userRole = authorityRepository.save(userRole);

        UserEntity admin = new UserEntity();
        admin.setNick("admin");
        admin.setMail("admin@admin.pl");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setAuthorities(Arrays.asList(adminRole, userRole));
        userRepository.save(admin);
    }

    private void setUpNieruchomosciCategory() {
        CategoryEntity nieruchomosci = CategoryEntity.builder().name("Nieruchomości").children(new ArrayList<>()).build();
        nieruchomosci = categoryRepository.save(nieruchomosci);

        CategoryEntity pokojeDoWynajecia = CategoryEntity.builder().name("pokoje do wynajęcia").parent(nieruchomosci).build();
        pokojeDoWynajecia = categoryRepository.save(pokojeDoWynajecia);

        CategoryEntity mieszkanieIdomyDoWynajecia = CategoryEntity.builder().name("mieszkania i domy do wynajecia").parent(nieruchomosci).build();
        mieszkanieIdomyDoWynajecia = categoryRepository.save(mieszkanieIdomyDoWynajecia);

        CategoryEntity mieszkaniaIdomySprzedam = CategoryEntity.builder().name("mieszkania i domy - sprzedam").parent(nieruchomosci).build();
        mieszkaniaIdomySprzedam = categoryRepository.save(mieszkaniaIdomySprzedam);

        nieruchomosci.getChildren().add(pokojeDoWynajecia);
        nieruchomosci.getChildren().add(mieszkanieIdomyDoWynajecia);
        nieruchomosci.getChildren().add(mieszkaniaIdomySprzedam);


        UserEntity user = userRepository.findByMailOrNick("admin").get();
        OfferEntity savedOffer = offerRepository.save(OfferEntity.builder()
                .title("jagodzianka")
                .price(150)
                .creator(user)
                .category(mieszkanieIdomyDoWynajecia)
                .publishDate(LocalDate.now())
                .content("Wiecie co to jagodzianka? To nie drożdżówka. Myśleliście, że to drożdżówka, a to nie drożdżówka")
                .build()
        );

        user.setOffers(List.of(savedOffer));
        mieszkanieIdomyDoWynajecia.setOffers(List.of(savedOffer));
    }

    private void setUpDomIogrodCategory() {
        CategoryEntity domIogrod = CategoryEntity.builder().name("Dom i Ogród").children(new ArrayList<>()).build();
        domIogrod = categoryRepository.save(domIogrod);

        CategoryEntity agd = CategoryEntity.builder().name("pokoje do wynajęcia").parent(domIogrod).build();
        agd = categoryRepository.save(agd);

        CategoryEntity meble = CategoryEntity.builder().name("meble").parent(domIogrod).build();
        meble = categoryRepository.save(meble);

        CategoryEntity narzedziaImaterialyBudowlane = CategoryEntity.builder().name("narzędzia i materiały budowlane").parent(domIogrod).build();
        narzedziaImaterialyBudowlane = categoryRepository.save(narzedziaImaterialyBudowlane);

        domIogrod.getChildren().add(agd);
        domIogrod.getChildren().add(meble);
        domIogrod.getChildren().add(narzedziaImaterialyBudowlane);

    }



}

