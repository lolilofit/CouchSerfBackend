package nsu.fit.upprpo.csbackend.security.data.types;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nsu.fit.upprpo.csbackend.PublicUtils;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Entity
@Table(name = "auth_roles")
public class SecuredUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_user_role_seq_gen")
    @SequenceGenerator(name = "sec_user_role_seq_gen", sequenceName = "SECURED_USER_ROLE_TRG")
    @Column(name = "link_id")
    private final Integer linkId;

    @Column(name = "user_id")
    private final Integer userId;

    @Column(name = "role_id")
    private final Integer roleId;

    @Override
    public String toString() {
        return PublicUtils.publicToString(this);
    }

    @Override
    public int hashCode() {
        int res = 0;
        if(linkId != null)
            res += linkId.hashCode();
        if(roleId != null)
            res += roleId.hashCode();
        if(userId != null)
            res += userId.hashCode();
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof SecuredUserRole))
            return false;
        SecuredUserRole securedUserRole = (SecuredUserRole) o;
        return securedUserRole.getLinkId().equals(linkId) &&
                securedUserRole.getRoleId().equals(roleId) &&
                securedUserRole.getUserId().equals(userId);

    }
}
