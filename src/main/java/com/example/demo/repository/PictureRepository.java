package com.example.demo.repository;

import com.example.demo.model.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    void deleteAllByPublicId(String publicId);


}
