package com.tuannq.store.service;

import com.tuannq.store.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    void save(Image img);

    List<Image> getAll();

    void deleteImage(String uploadDir, String filename);

    List<String> getListImageOfUser(Long userId);
}
