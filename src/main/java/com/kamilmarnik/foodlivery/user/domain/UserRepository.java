package com.kamilmarnik.foodlivery.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByUsername(String username);

}
