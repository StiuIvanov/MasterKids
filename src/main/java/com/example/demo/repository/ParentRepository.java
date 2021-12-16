package com.example.demo.repository;

import com.example.demo.model.entity.ParentEntity;
import com.example.demo.model.service.ParentWithoutChildrenServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

    Optional<ParentEntity> findUserByUsernameAndPassword(String username, String password);


    Optional<ParentEntity> findByUsername(String username);

    Optional<ParentEntity> findByUsernameIgnoreCase(String username);

    Optional<ParentEntity> findByEmail(String email);

    @Query("select p from ParentEntity p where size(p.children) = 0")
    List<ParentEntity> findParentEntitiesByChildEntitiesIsNull();

    @Query("SELECT new com.example.demo.model.service.ParentWithoutChildrenServiceModel(p.username) from ParentEntity p where size(p.children) = 0 ")
    List<ParentWithoutChildrenServiceModel> findInactiveParents();
}
