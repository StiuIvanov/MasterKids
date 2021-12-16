package com.example.demo.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudinaryImageTest {

    private static final String URL= "www.test.com";
    private static final String PUBLIC_ID= "testStringPublicId";
    private CloudinaryImage cloudinaryImage;

    @BeforeEach
    void setUp(){
        cloudinaryImage = new CloudinaryImage()
                .setUrl(URL)
                .setPublicId(PUBLIC_ID);
    }

    @Test
    void getUrlShouldReturnCorrectUrl() {
        assertEquals(cloudinaryImage.getUrl(), URL);
    }

    @Test
    void getPublicIDShouldReturnCorrectId(){
        assertEquals(cloudinaryImage.getPublicId(),PUBLIC_ID);
    }
}