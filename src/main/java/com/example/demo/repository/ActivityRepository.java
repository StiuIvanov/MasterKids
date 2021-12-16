package com.example.demo.repository;

import com.example.demo.model.entity.ActivityEntity;
import com.example.demo.model.entity.ChildEntity;
import com.example.demo.model.entity.enums.ActivityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

Optional<ActivityEntity> findByName(ActivityEnum name);

List<ActivityEntity> findAllByName(ActivityEnum name);
}
