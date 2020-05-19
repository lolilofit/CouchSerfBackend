package main.rm.security.data.types;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "ROLE_TRG")
    @Column(name = "role_id")
    private final Integer roleId;

    @NotEmpty
    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<SecuredUser> users;

    public enum Roles {
        USER_ROLE, ADMIN_ROLE;
    }
}
