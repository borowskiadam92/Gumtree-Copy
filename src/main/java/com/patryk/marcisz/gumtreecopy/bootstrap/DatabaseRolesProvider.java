package com.patryk.marcisz.gumtreecopy.bootstrap;

import com.patryk.marcisz.gumtreecopy.model.dao.AuthorityEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.UserEntity;
import com.patryk.marcisz.gumtreecopy.repository.AuthoritiesRepository;
import com.patryk.marcisz.gumtreecopy.repository.CategoriesRepository;
import com.patryk.marcisz.gumtreecopy.repository.OffersRepository;
import com.patryk.marcisz.gumtreecopy.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Order(1)
public class DatabaseRolesProvider implements CommandLineRunner {

    private final AuthoritiesRepository authoritiesRepository;
    private final CategoriesRepository categoriesRepository;
    private final OffersRepository offersRepository;
    private final UsersRepository usersRepository;
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
        adminRole = authoritiesRepository.save(adminRole);

        AuthorityEntity userRole = new AuthorityEntity();
        userRole.setName("ROLE_USER");
        userRole = authoritiesRepository.save(userRole);

        UserEntity admin = new UserEntity();
        admin.setNick("admin");
        admin.setMail("admin@admin.pl");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setAuthorities(Arrays.asList(adminRole, userRole));
        usersRepository.save(admin);
    }

    private void setUpNieruchomosciCategory() {
        CategoryEntity nieruchomosci = CategoryEntity.builder().name("Nieruchomości").children(new ArrayList<>()).build();
        nieruchomosci = categoriesRepository.save(nieruchomosci);

        CategoryEntity pokojeDoWynajecia = CategoryEntity.builder().name("pokoje do wynajęcia").parent(nieruchomosci).build();
        pokojeDoWynajecia = categoriesRepository.save(pokojeDoWynajecia);

        CategoryEntity mieszkanieIdomyDoWynajecia = CategoryEntity.builder().name("mieszkania i domy do wynajecia").parent(nieruchomosci).build();
        mieszkanieIdomyDoWynajecia = categoriesRepository.save(mieszkanieIdomyDoWynajecia);

        CategoryEntity mieszkaniaIdomySprzedam = CategoryEntity.builder().name("mieszkania i domy - sprzedam").parent(nieruchomosci).build();
        mieszkaniaIdomySprzedam = categoriesRepository.save(mieszkaniaIdomySprzedam);

        nieruchomosci.getChildren().add(pokojeDoWynajecia);
        nieruchomosci.getChildren().add(mieszkanieIdomyDoWynajecia);
        nieruchomosci.getChildren().add(mieszkaniaIdomySprzedam);


        offersRepository.save(OfferEntity.builder()
                .title("jagodzianka")
                .price(150)
                .creator(usersRepository.findByMailOrNick("admin").get())
                .category(mieszkanieIdomyDoWynajecia)
                .publishDate(LocalDate.now())
                .content("Wiecie co to jagodzianka? To nie drożdżówka. Myśleliście, że to drożdżówka, a to nie drożdżówka")
                .build()
        );
    }

    private void setUpDomIogrodCategory() {
        CategoryEntity domIogrod = CategoryEntity.builder().name("Dom i Ogród").children(new ArrayList<>()).build();
        domIogrod = categoriesRepository.save(domIogrod);

        CategoryEntity agd = CategoryEntity.builder().name("pokoje do wynajęcia").parent(domIogrod).build();
        agd = categoriesRepository.save(agd);

        CategoryEntity meble = CategoryEntity.builder().name("meble").parent(domIogrod).build();
        meble = categoriesRepository.save(meble);

        CategoryEntity narzedziaImaterialyBudowlane = CategoryEntity.builder().name("narzędzia i materiały budowlane").parent(domIogrod).build();
        narzedziaImaterialyBudowlane = categoriesRepository.save(narzedziaImaterialyBudowlane);

        domIogrod.getChildren().add(agd);
        domIogrod.getChildren().add(meble);
        domIogrod.getChildren().add(narzedziaImaterialyBudowlane);
    }

}

