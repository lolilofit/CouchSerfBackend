package nsu.fit.upprpo.csbackend.security.data.types;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.fit.upprpo.csbackend.PublicUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable {
    @Id
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
        USER_ROLE, ADMIN_ROLE
    }

    @Override
    public String toString() {
        return PublicUtils.publicToString(this);
    }

    @Override
    public int hashCode() {
        int hc = users.stream().mapToInt(SecuredUser::hashCode).sum();
        return roleName.hashCode() + hc;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof  Role)) {
            return false;
        }
        Role role = (Role) o;
        List<SecuredUser> list = role.getUsers();

        if(this.users.size() != list.size())
            return false;
        for(int i = 0; i < list.size(); i++)
            if(!list.get(i).equals(this.users.get(i)))
                return false;

        return this.roleName.equals(role.getRoleName());
    }
}
