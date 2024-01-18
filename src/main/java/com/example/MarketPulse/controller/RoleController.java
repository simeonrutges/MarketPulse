package com.example.MarketPulse.controller;

import com.example.MarketPulse.dto.RoleDto;
import com.example.MarketPulse.service.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public String createRole(@RequestBody RoleDto dto) {
        String dto1 = roleService.createRole(dto);
        return dto1;
    }
}
