package com.nab.icommerce.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Colour {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @NotNull
    String name;

    @Column(name = "colour_hex_value")
    String colourHexValue;
}