package com.nab.icommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @NotNull
    String username;
    @NotNull
    @JsonIgnore
    @Column(name = "password_hash")
    String passwordHash;
    @Column(name = "display_name")
    String displayName;
    String mobile;
    String email;
    String address;
}
