package com.example.demo.service;

import com.example.demo.model.entity.PictureEntity;
import com.example.demo.model.view.PictureViewModel;

import java.util.List;

public interface PictureService {
    void saveToDB(PictureEntity picture);

    List<PictureViewModel> findAllPictures();

    void deleteByPublicId(String publicId);

}
