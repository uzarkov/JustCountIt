package com.justcountit.commons;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Currency {

    USD("USD", "$"),
    PLN("PLN", "zł"),
    EURO("EUR", "€");

    private final String name;
    private final String symbol;
}
