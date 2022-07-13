package com.example.demo.store.entities;

import com.example.demo.authorization.entities.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordering")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOrder;

    @ManyToMany(fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "orders",
            joinColumns = @JoinColumn(
                    name = "order_id", referencedColumnName = "idOrder"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "idProduct"))
    private Collection<ProductEntity> productEntities;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    private AccountEntity account;
}
