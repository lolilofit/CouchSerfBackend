package nsu.fit.upprpo.csbackend.security;

import nsu.fit.upprpo.csbackend.security.data.JpaRoleLinkRepository;
import nsu.fit.upprpo.csbackend.security.data.JpaRoleRepository;
import nsu.fit.upprpo.csbackend.security.data.types.Role;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RoleHelper {

    private final JpaRoleLinkRepository roleLinkRepository;
    private final JpaRoleRepository roleRepository;

    @Autowired
    public RoleHelper(JpaRoleLinkRepository roleLinkRepository, JpaRoleRepository roleRepository) {
        this.roleLinkRepository = roleLinkRepository;
        this.roleRepository = roleRepository;
    }

    public SecuredUserRole addRoleTo(Integer uid, Role.Roles role) {
        Optional<Role> foundRole = roleRepository.findByRoleName(role.toString());

        Role resultRole;
        resultRole = foundRole.orElseGet(() -> roleRepository.save(new Role(null, role.toString(), new ArrayList<>())));
        return roleLinkRepository.save(new SecuredUserRole(null, uid, resultRole.getRoleId()));
    }
}
