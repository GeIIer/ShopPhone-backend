package com.example.demo.api.service;

import com.example.demo.store.entities.ImageEntity;
import com.example.demo.store.entities.ProductEntity;
import com.example.demo.store.repositories.ImageRepository;
import com.example.demo.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    private final ProductRepository productRepository;


    public void save(MultipartFile image, Long idProduct) throws IOException {
        ImageEntity fileEntity = new ImageEntity();
        fileEntity.setName(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        fileEntity.setContentType(image.getContentType());
        fileEntity.setData(image.getBytes());
        fileEntity.setSize(image.getSize());

        ProductEntity entity = productRepository.findByIdProduct(idProduct);
        entity.addImgProduct(fileEntity);
        imageRepository.save(fileEntity);
    }

    public Optional<ImageEntity> getFile(String id) {
        return imageRepository.findById(id);
    }

    public List<ImageEntity> getAllFiles() {
        return imageRepository.findAll();
    }
}
