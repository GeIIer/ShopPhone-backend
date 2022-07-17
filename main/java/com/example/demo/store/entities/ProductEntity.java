package com.example.demo.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idProduct;
    private String nameProduct;
    private String characteristic;
    private String description;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private СategoryEntity category;

    @OneToMany (fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    private List<ImageEntity> imageEntityList = new ArrayList<>();

    private double price;

    public void addImgProduct(ImageEntity entity) {
        this.imageEntityList.add(entity);
        entity.setProduct(this);
    }

}
