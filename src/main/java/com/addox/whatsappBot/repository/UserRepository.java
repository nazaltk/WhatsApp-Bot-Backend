package com.addox.whatsappBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.addox.whatsappBot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
