package com.justcountit.group.membership;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Embeddable
public class GroupMembershipKey implements Serializable {

    @Column(name = "appuser_id")
    private Long appUserId;

    @Column(name = "group_id")
    private Long groupId;

}
