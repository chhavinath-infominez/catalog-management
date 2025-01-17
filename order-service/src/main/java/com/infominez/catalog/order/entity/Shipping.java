package com.infominez.catalog.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infominez.catalog.order.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipping")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shipping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    private Long shippingId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "courier_service", length = 100)
    private String courierService;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "estimated_delivery_date")
    private Date estimatedDeliveryDate;

    @Column(name = "actual_delivery_date")
    private Date actualDeliveryDate;

    @Column(name = "shipping_status", length = 50)
    private String shippingStatus;

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "delivery_attempts", nullable = false)
    private Integer deliveryAttempts = 0;

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "shipping_date", nullable = false)
    private LocalDateTime shippingDate;

    @Column(name = "shipping_notes", columnDefinition = "TEXT")
    private String shippingNotes;

    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "dimensions", length = 100)
    private String dimensions;

}
