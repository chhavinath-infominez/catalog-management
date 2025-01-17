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
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "payment_status", nullable = false, length = 50)
    private String paymentStatus;

    @Column(name = "payment_mode", nullable = false, length = 50)
    private String paymentMode;

    @Column(name = "order_type", nullable = false, length = 50)
    private String orderType;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "handling_fee", precision = 10, scale = 2)
    private BigDecimal handlingFee;

    @Column(name = "total_amount_after_discount", precision = 10, scale = 2)
    private BigDecimal totalAmountAfterDiscount;

    @Column(name = "total_amount_with_tax", precision = 10, scale = 2)
    private BigDecimal totalAmountWithTax;

    @Column(name = "payable_amount", precision = 10, scale = 2)
    private BigDecimal payableAmount;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "delivery_instructions", columnDefinition = "TEXT")
    private String deliveryInstructions;

    @Column(name = "billing_address_line1")
    private String billingAddressLine1;

    @Column(name = "billing_address_line2")
    private String billingAddressLine2;

    @Column(name = "billing_city")
    private String billingCity;

    @Column(name = "billing_state")
    private String billingState;

    @Column(name = "billing_country")
    private String billingCountry;

    @Column(name = "billing_postal_code")
    private String billingPostalCode;

    @Column(name = "billing_contact_number", length = 15)
    private String billingContactNumber;

    @Column(name = "order_reference_number")
    private String orderReferenceNumber;

    @Column(name = "promo_code")
    private String promoCode;
}
