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
@Table(name = "category")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "slug", unique = true, length = 150)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    private Category parentCategory;

    @Column(name = "meta_title", length = 150)
    private String metaTitle;

    @Column(name = "meta_keywords", columnDefinition = "TEXT")
    private String metaKeywords;

    @Column(name = "meta_description", columnDefinition = "TEXT")
    private String metaDescription;
}

