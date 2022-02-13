package com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Value
public class ResponseDto {
    String name;
    List<String> topLevelDomain;
    String alpha2Code;
    String alpha3Code;
    List<String> callingCodes;
    String capital;
    List<String> altSpellings;
    String region;
    String subregion;
    int population;
    List<Integer> latlng;
    String demonym;
    int area;
    double gini;
    List<String> timezones;
    List<String> borders;
    String nativeName;
    String numericCode;
    List<CurrencyResponseDto> currencies;
    List<LanguageResponseDto> languages;
    TranslationsResponseDto translations;
    String flag;
    List<RegionalBlocResponseDto> regionalBlocs;
    String cioc;
}
