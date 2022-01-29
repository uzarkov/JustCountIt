package com.justcountit.group;

import com.justcountit.commons.Currency;

import java.util.Map;

public record GroupMetadata(Long id,
                            String name,
                            String description,
                            Currency currency,
                            Map<Long, MembershipMetadata> members) {

}
