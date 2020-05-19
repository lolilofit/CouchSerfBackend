package main.rm.security.data.types;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
}
