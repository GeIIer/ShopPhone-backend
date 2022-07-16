package com.example.demo.api.controllers;

import com.example.demo.api.dto.OrderDTO;
import com.example.demo.api.dto.ProductDTO;
import com.example.demo.api.factory.OrderFactory;
import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.authorization.repositories.AccountRepository;
import com.example.demo.store.entities.OrderEntity;
import com.example.demo.store.entities.ProductEntity;
import com.example.demo.store.repositories.OrderRepository;
import com.example.demo.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public static final String GET_ORDERS = "/orders";

    public static final String CREATE_NEW_ORDER = "/order/add";

    public static final String DELETE_ORDER = "/order/{order_id}";

    @GetMapping(GET_ORDERS)
    public List<OrderDTO> getOrders(@RequestParam(value = "email", required = false) String email) {
        if (email != null) {
            if (accountRepository.existsAccountEntityByEmail(email)) {
                AccountEntity accountEntity = accountRepository.findByEmail(email);
                List<OrderEntity> orderEntities = orderRepository.findAllByAccount_Id(accountEntity.getId());
                return orderEntities.stream().map(orderFactory::makeOrderDTO).collect(Collectors.toList());
            }
            else return null;
        }
        else return null;
    }

    @PostMapping(CREATE_NEW_ORDER)
    public ResponseEntity<String> addOrder (@RequestParam(value = "email", required = false) String email,
                                            @RequestParam(value = "price", required = false) double price,
                                            @RequestBody ProductDTO[] productDTO) {
        if (email != null &&productDTO != null && productDTO.length > 0 && price > 0) {
            if (accountRepository.existsAccountEntityByEmail(email)) {

                OrderEntity entity = new OrderEntity();
                entity.setAccount(accountRepository.findByEmail(email));
                ArrayList<ProductEntity> productEntities = new ArrayList<>();
                for (int i = 0; i < productDTO.length; i++){
                    productEntities.add(productRepository.findByIdProduct(productDTO[i].getIdProduct()));
                }
                entity.setProductEntities(productEntities);
                entity.setTotalPrice(price);
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

    @DeleteMapping(DELETE_ORDER)
    public ResponseEntity<String> deleteProject(@PathVariable("order_id") Long orderId) {

        if(!orderRepository.existsByIdOrder(orderId)) {
            return ResponseEntity.badRequest().body("order not found");
        }
        orderRepository.deleteById(orderId);

        return ResponseEntity.ok("delete "+orderId);
    }
}
