package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// pass the type that we want represented as rows in the db, as well as the type of its primary key (id)
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
