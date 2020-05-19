package main.rm.security.data.types;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Entity
@Table(name = "secus")
public class SecuredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "su_seq_gen")
    @SequenceGenerator(name = "su_seq_gen", sequenceName = "SECURED_USER_TRG")
    @Column(name = "user_id")
    private final Integer userId;

    @NotEmpty
    @Column(name = "username", unique = true)
    private final String username;

    @NotEmpty
    @Column(name = "password")
   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable( name = "auth_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    @JsonIgnore
    private List<Role> roles;
/*
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("[userId:").append(userId).append(", ");
        buffer.append("username:").append(username).append(", ");
        buffer.append("password:").append(password).append(", ");

        buffer.append("roles:[");
        roles.forEach(role -> buffer.append(role.getRoleName()).append(", "));
        buffer.append("]]");

        return buffer.toString();
    }
*/
    public List<String> getStringRoles() {
        List<String> result = new ArrayList<>();
        roles.forEach(role -> result.add(role.getRoleName()));
        return result;
    }
}
