package com.justcountit.expenditure;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface ExpenditureMetadataProjection {
    Long getId();

    Double getPrice();

    String getTitle();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("date")
    LocalDateTime getExpenditureDate();

    @Value("#{target.creator.name}")
    String getUsername();

    @Value("#{target.creator.id}")
    Long getUserId();
}
