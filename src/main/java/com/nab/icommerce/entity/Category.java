package com.nab.icommerce.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @Column(name = "category_parent_id")
    Integer categoryParentId;

    @NotNull
    String name;
}