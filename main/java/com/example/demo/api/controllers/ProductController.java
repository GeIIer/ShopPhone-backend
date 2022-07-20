package com.example.demo.api.controllers;

import com.example.demo.api.dto.ProductDTO;
import com.example.demo.api.factory.ProductDTOFactory;
import com.example.demo.api.service.ImageService;
import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.store.entities.CategoryEntity;
import com.example.demo.store.entities.ManufacturerEntity;
import com.example.demo.store.entities.ProductEntity;
import com.example.demo.store.repositories.CategoryRepository;
import com.example.demo.store.repositories.ManufacturerRepository;
import com.example.demo.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping({"/api/products"})
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ManufacturerRepository manufacturerRepository;
    @Autowired
    private final ProductDTOFactory productDTOFactory;
    @Autowired
    private final ImageService imageService;

    private static final String GET_PRODUCT = "/product";

    private static final String CREATE_PRODUCT = "/product";

    @PostMapping(CREATE_PRODUCT)
    public ResponseEntity<String> createNewProduct(@RequestBody ProductDTO newProduct,
                                                   @RequestParam("file") MultipartFile file) {
        if (newProduct == null) {
            return ResponseEntity.badRequest().body("Error: нет данных");
        }
        if (productRepository.existsByNameProduct(newProduct.getNameProduct())) {
            return ResponseEntity.badRequest().body("Error: такой продукт уже существует");
        }
        if (!categoryRepository.existsByNameCategory(newProduct.getCategoryDTO())) {
            return ResponseEntity.badRequest().body("Error: такой категории не существует");
        }
        if (!manufacturerRepository.existsByNameManuFact(newProduct.getManufacturerDTO())) {
            return ResponseEntity.badRequest().body("Error: такого производителя не существует");
        }
        ProductEntity entity = new ProductEntity();
        CategoryEntity categoryEntity = categoryRepository.findByNameCategory(newProduct.getCategoryDTO());
        ManufacturerEntity manufacturerEntity = manufacturerRepository.findByNameManuFact(newProduct.getManufacturerDTO());
        entity.setNameProduct(newProduct.getNameProduct());
        entity.setCategory(categoryEntity);
        entity.setManufacturer(manufacturerEntity);
        entity.setCharacteristic(newProduct.getCharacteristic());
        entity.setDescription(newProduct.getDescription());
        try {
            imageService.save(file, entity.getIdProduct());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Картинка загружена: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Ошибка загрузки файла: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping()
    public List<ProductDTO> getProducts(@RequestParam(value = "category_id", required = false) Long optionalCategoryId) {

        if (optionalCategoryId != null){
          return (productRepository.findAllByCategory_IdCategory(optionalCategoryId)
                  .stream().map(productDTOFactory::makeProductDTO)
                  .collect(Collectors.toList()));
        }
        else {
            return (productRepository
                    .findAll())
                    .stream()
                    .map(productDTOFactory::makeProductDTO)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping(GET_PRODUCT)
    public ProductDTO getProduct (@RequestParam(value = "product", required = false) Long idProduct) {
        ProductEntity entity = productRepository.findByIdProduct(idProduct);
        return productDTOFactory.makeProductDTOWithImage(entity);
    }
}
