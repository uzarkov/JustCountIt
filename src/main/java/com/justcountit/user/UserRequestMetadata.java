package com.justcountit.user;

import java.util.List;

public record UserRequestMetadata(List<ForDebtorsMetadata> forDebtors, List<ForMeMetadata> forMe) {



}
