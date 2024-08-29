package practice.com.online_learning_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.com.online_learning_platform.Repository.RoleRepository;
import practice.com.online_learning_platform.entity.Role;
import practice.com.online_learning_platform.utility.enums.RoleEnum;



@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(RoleEnum role) {
        return roleRepository.findByName(role).orElse(null);
    }
}
