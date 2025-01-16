package com.infominez.catalog.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infominez.catalog.product.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_inventory")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductInventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_variant_id", nullable = false, unique = true)
    private ProductVariant productVariant;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;
}
