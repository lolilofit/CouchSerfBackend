package nsu.fit.upprpo.csbackend.security.data.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "secus")
public class SecuredUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "su_seq_gen")
    @SequenceGenerator(name = "su_seq_gen", sequenceName = "SECURED_USER_TRG")
    @Column(name = "user_id")
    private Integer userId;

    @NotEmpty
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty
    @Column(name = "password")
     //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable( name = "auth_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    @JsonIgnore
    private List<Role> roles;

    public List<String> getStringRoles() {
        List<String> result = new ArrayList<>();
        if (roles != null)
            roles.forEach(role -> result.add(role.getRoleName()));
        return result;
    }
}
