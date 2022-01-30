package com.justcountit.group;

import com.justcountit.commons.Currency;

public record GroupBaseData(Long Id, String name, Currency currency ) {

    public GroupBaseData(String name, Currency currency) {
        this(null, name, currency);
    }

    public Group toGroup() {
        return new Group(name, currency);
    }

    public static GroupBaseData from(Group group) {
        return new GroupBaseData(group.getId(), group.getName(), group.getCurrency());
    }
}
