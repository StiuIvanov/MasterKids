package com.example.demo.service.impl;

import com.example.demo.model.entity.PictureEntity;
import com.example.demo.model.view.PictureViewModel;
import com.example.demo.repository.PictureRepository;
import com.example.demo.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void saveToDB(PictureEntity picture) {
        pictureRepository.save(picture);
    }

    @Override
    public List<PictureViewModel> findAllPictures() {
        return pictureRepository
                .findAll()
                .stream()
                .map(this::asViewModel)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteByPublicId(String publicId) {
        pictureRepository.deleteAllByPublicId(publicId);
    }


    private PictureViewModel asViewModel(PictureEntity picture) {
        return new PictureViewModel()
                .setPublicId(picture.getPublicId())
                .setTitle(picture.getTitle())
                .setUrl(picture.getUrl());
    }
}
