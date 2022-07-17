package com.example.demo.api.controllers;

import com.example.demo.api.dto.ImageDTO;
import com.example.demo.api.factory.ImageDTOFactory;
import com.example.demo.api.service.ImageService;
import com.example.demo.store.entities.ImageEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping({"/api"})
public class ImageController {

    @Autowired
    private final ImageService imageService;
    @Autowired
    private final ImageDTOFactory imageDtoFactory;

    private static final String UPLOAD_IMAGES = "/images";

    private static final String GET_IMAGES = "/images/all";
    private static final String GET_IMAGE = "/images/{id}";

    @PostMapping(UPLOAD_IMAGES)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("idProduct") Long idProduct) {
        try {
            imageService.save(file, idProduct);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Картинка загружена: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Ошибка загрузки файла: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping(GET_IMAGES)
    public List<ImageDTO> list() {
        return imageService.getAllFiles()
                .stream()
                .map(imageDtoFactory::makeImageDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(GET_IMAGE)
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<ImageEntity> fileEntityOptional = imageService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        ImageEntity fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }
}
