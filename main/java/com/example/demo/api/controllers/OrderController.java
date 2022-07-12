package com.example.demo.api.controllers;

import com.example.demo.api.dto.OrderDTO;
import com.example.demo.api.dto.ProductDTO;
import com.example.demo.api.factory.OrderFactory;
import com.example.demo.api.factory.ProductDTOFactory;
import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.authorization.repositories.AccountRepository;
import com.example.demo.store.entities.OrderEntity;
import com.example.demo.store.entities.ProductEntity;
import com.example.demo.store.repositories.OrderRepository;
import com.example.demo.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping({"/api"})
public class OrderController {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderFactory orderFactory;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public static final String GET_PRODUCTS = "/orders";

    @GetMapping(GET_PRODUCTS)
    public List<OrderDTO> getOrders() {
        return (orderRepository.findAll())
                .stream()
                .map(orderFactory::makeOrderDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/addOrder")
    public ResponseEntity<String> addOrder (@RequestParam(value = "email", required = false) String email,
                                            @RequestBody ProductDTO[] productDTO) {
        if (email != null &&productDTO != null && productDTO.length > 0) {
            if (accountRepository.existsAccountEntityByEmail(email)) {

                OrderEntity entity = new OrderEntity();
                entity.setIdOrder(orderRepository.count()+1);
                entity.setAccount(accountRepository.findByEmail(email));
                ArrayList<ProductEntity> productEntities = new ArrayList<>();
                for (int i = 0; i < productDTO.length; i++){
                    productEntities.add(productRepository.findByIdProduct(productDTO[i].getIdProduct()));
                }
                entity.setProductEntities(productEntities);
                orderRepository.save(entity);
            }
            else {
                ResponseEntity.badRequest().body("Пользователя не существует");
            }
        }
        else {
            ResponseEntity.badRequest().body("Продуктов нет");
        }
        return ResponseEntity.ok("addOrder");
    }
}
