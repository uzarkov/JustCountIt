package com.justcountit.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justcountit.commons.Currency;

import java.util.Map;

public record GroupMetadata(Long id,
                            String name,
                            String description,
                            @JsonFormat(shape = JsonFormat.Shape.OBJECT) Currency currency,
                            Map<Long, MembershipMetadata> members) {

}
