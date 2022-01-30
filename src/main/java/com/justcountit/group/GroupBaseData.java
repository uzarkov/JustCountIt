package com.justcountit.group;

import com.justcountit.commons.Currency;

public record GroupBaseData(Long id, String name, String description, Currency currency ) {

    public GroupBaseData(String name, Currency currency) {
        this(null, name, name, currency);
    }

    public Group toGroup() {
        return new Group(name, currency);
    }

    public static GroupBaseData from(Group group) {
        return new GroupBaseData(group.getId(), group.getName(), group.getDescription(), group.getCurrency());
    }
}
