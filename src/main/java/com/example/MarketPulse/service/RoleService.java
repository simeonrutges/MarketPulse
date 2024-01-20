package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.RoleDto;
import com.example.MarketPulse.model.Role;
import com.example.MarketPulse.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepos;

    public RoleService(RoleRepository roleRepos) {
        this.roleRepos = roleRepos;
    }
    public String createRole(RoleDto roleDto) {
        Role newRole = new Role();
        newRole.setRolename(roleDto.rolename);
        roleRepos.save(newRole);

        return "Done";
    }
}
