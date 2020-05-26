package nsu.fit.upprpo.csbackend.security.data.types;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Entity
@Table(name = "auth_roles")
public class SecuredUserRole {
    private static final Logger logger = Logger.getLogger(SecuredUserRole.class);

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
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
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
