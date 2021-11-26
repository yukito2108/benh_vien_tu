package com.tuannq.store.service.impl;

import com.tuannq.store.entity.Image;
import com.tuannq.store.exception.BadRequestException;
import com.tuannq.store.exception.InternalServerException;
import com.tuannq.store.repository.ImageRepository;
import com.tuannq.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Component
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void save(Image img) {
        imageRepository.save(img);
    }

    @Override
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = InternalServerException.class)
    public void deleteImage(String uploadDir, String filename) {
        String link = "/media/static/" + filename;
        Image img = imageRepository.findByLink(link);
        if (img == null) {
            throw new BadRequestException("File không tồn tại");
        }

        imageRepository.delete(img);

        File file = new File(uploadDir + "/" + filename);
        if (file.exists()) {
            if (!file.delete()) {
                throw new InternalServerException("Lỗi khi xóa ảnh");
            }
        }
    }

    @Override
    public List<String> getListImageOfUser(Long userId) {
        return imageRepository.getListImageOfUser(userId);
    }
}
