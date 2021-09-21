package com.patryk.marcisz.gumtreecopy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.CreateOfferRequest;
import com.patryk.marcisz.gumtreecopy.model.dto.offer.OfferResponse;
import com.patryk.marcisz.gumtreecopy.service.CreateOfferService;
import com.patryk.marcisz.gumtreecopy.service.GetOffersService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;

import static com.patryk.marcisz.gumtreecopy.TestUtils.inputStreamtoString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OffersController.class)
class OffersControllerIT {

    @MockBean
    private GetOffersService getOffersService;

    @MockBean
    private CreateOfferService createOfferService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    //sposob na wczytanie plikow z folderu resources
    @Value("classpath:userOffers/get-offer-1-response.json")
    private Resource getOfferResponse;

    @Value("classpath:userOffers/post-offer-1-request.json")
    private Resource createOfferRequest;


    @Test
    void shouldReturnOfferIfExists() throws Exception {
        when(getOffersService.getOfferById(BigInteger.ONE))
                .thenReturn(objectMapper.readValue(inputStreamtoString(getOfferResponse.getInputStream()), OfferResponse.class));

        mockMvc.perform(get("/api/offers/1"))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("jagodzianka")))
                .andExpect(jsonPath("$.content", equalTo("Wiecie co to jagodzianka? To nie drożdżówka. " +
                        "Myśleliście, że to drożdżówka, a to nie drożdżówka")))
                .andExpect(jsonPath("$.price", equalTo(120)))
                .andExpect(jsonPath("$.creatorId", equalTo(1)))
                .andExpect(jsonPath("$.categoryId", equalTo(1)))
                .andExpect(jsonPath("$.publishDate", equalTo("2021-02-02")))
                .andDo(print());
    }

    @Test
    @WithMockUser //wazne zeby to dodac, zeby przepuscilo nas security, przy get nie trzeba roli
    void shouldSaveOffer() throws Exception {
        mockMvc.perform(post("/api/offers")
                .content(inputStreamtoString(createOfferRequest.getInputStream()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        ArgumentCaptor<CreateOfferRequest> argumentCaptor = ArgumentCaptor.forClass(CreateOfferRequest.class);

        verify(createOfferService, times(1)).createOffer(argumentCaptor.capture());

        CreateOfferRequest value = argumentCaptor.getValue();
        assertEquals(1, value.getCategoryId());
        assertEquals("jagodzianka", value.getTitle());
        assertEquals("Wiecie co to jagodzianka? To nie drożdżówka. Myśleliście, że to drożdżówka, a to nie drożdżówka", value.getContent());
        assertEquals(120, value.getPrice());

    }


}
