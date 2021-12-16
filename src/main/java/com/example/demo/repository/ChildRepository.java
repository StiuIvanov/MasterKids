package com.example.demo.repository;

import com.example.demo.model.entity.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

}
