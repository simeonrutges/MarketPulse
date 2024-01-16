package com.example.MarketPulse.repository;

import com.example.MarketPulse.model.Role;
import com.example.MarketPulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
