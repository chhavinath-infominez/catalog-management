package com.infominez.catalog.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infominez.catalog.order.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order orderId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "product_sku", length = 100)
    private String productSku;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "final_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal finalPrice;

    @Column(name = "product_category", length = 100)
    private String productCategory;

    @Column(name = "product_weight", precision = 10, scale = 2)
    private BigDecimal productWeight;

    @Column(name = "product_dimensions", length = 100)
    private String productDimensions;

    @Column(name = "product_color", length = 50)
    private String productColor;

    @Column(name = "product_size", length = 50)
    private String productSize;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "is_returnable", nullable = false)
    private Boolean isReturnable = true;

    @Column(name = "return_policy", columnDefinition = "TEXT")
    private String returnPolicy;

    @Column(name = "customization_details", columnDefinition = "TEXT")
    private String customizationDetails;
}
