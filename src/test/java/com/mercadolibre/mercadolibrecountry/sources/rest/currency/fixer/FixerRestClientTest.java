package com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer;

import com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response.ErrorResponseDto;
import com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response.InfoResponseDto;
import com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response.QueryResponseDto;
import com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FixerRestClientTest {

    @MockBean
    private FixerFeignClient feignClient;

    @Autowired
    private FixerRestClient fixerRestClient;

    @Test
    void when_cop_to_usd_then_success() {
        Double expected = 0.000266;

        QueryResponseDto queryResponseDto = QueryResponseDto.builder()
                .from("COP")
                .to("USD")
                .amount(1).build();

        InfoResponseDto infoResponseDto = InfoResponseDto.builder()
                .timestamp(System.currentTimeMillis())
                .rate(expected).build();
        ResponseDto responseDto = ResponseDto.builder()
                .success(true)
                .query(queryResponseDto)
                .info(infoResponseDto)
                .date("2021-09-01")
                .result(expected).build();

        doReturn(responseDto).when(feignClient).get(anyString(),anyString(), anyString(), any(), any());

        Double response = fixerRestClient.findRate("COP", "USD");
        assertEquals(expected, response);
    }

    @Test
    void when_cap_to_usd_then_failure() {

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(402)
                .type("invalid_from_currency")
                .info("You have entered an invalid from property. [Example: from=EUR]").build();

        ResponseDto responseDto = ResponseDto.builder()
                .success(false)
                .error(errorResponseDto).build();

        doReturn(responseDto).when(feignClient).get(anyString(),anyString(), anyString(), any(), any());

        Double response = fixerRestClient.findRate("COP", "USD");
        assertEquals( 0, response );
    }

    @Test
    void when_cap_to_usd_then_return_zero()
    {
        doReturn(null).when(feignClient).get(anyString(),anyString(), anyString(), any(), any());

        Double response = fixerRestClient.findRate("COP", "USD");
        assertEquals( 0, response );
    }

}