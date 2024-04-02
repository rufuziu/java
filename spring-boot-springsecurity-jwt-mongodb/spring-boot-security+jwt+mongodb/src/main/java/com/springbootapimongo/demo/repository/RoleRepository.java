package com.springbootapimongo.demo.repository;

import com.springbootapimongo.demo.models.ERole;
import com.springbootapimongo.demo.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
  Optional<Role> findByName(ERole name);
}
