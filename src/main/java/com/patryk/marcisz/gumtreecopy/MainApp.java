package com.patryk.marcisz.gumtreecopy;

import com.patryk.marcisz.gumtreecopy.model.dao.CategoryEntity;
import com.patryk.marcisz.gumtreecopy.model.dao.OfferEntity;
import com.patryk.marcisz.gumtreecopy.repository.CategoryRepository;
import com.patryk.marcisz.gumtreecopy.repository.OffersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
@SpringBootApplication
@RequiredArgsConstructor
public class MainApp implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    private final CategoryRepository categoryRepository;
    private final OffersRepository offersRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        setUpNieruchomosciCategory();
        setUpDomIogrodCategory();
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


        offersRepository.save(OfferEntity.builder()
                .title("jagodzianka")
                .price(150)
                .category(mieszkanieIdomyDoWynajecia)
                .publishDate(LocalDate.now())
                .content("Wiecie co to jagodzianka? To nie drożdżówka. Myśleliście, że to drożdżówka, a to nie drożdżówka")
                .build());
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
