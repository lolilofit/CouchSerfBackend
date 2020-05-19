package main.rm.security;

import main.rm.security.data.JpaRoleLinkRepository;
import main.rm.security.data.JpaRoleRepository;
import main.rm.security.data.types.Role;
import main.rm.security.data.types.SecuredUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (!foundRole.isPresent())
            resultRole = roleRepository.save(new Role(null, role.toString(), null));
        else
            resultRole = foundRole.get();
        return roleLinkRepository.save(new SecuredUserRole(null, uid, resultRole.getRoleId()));
    }
}