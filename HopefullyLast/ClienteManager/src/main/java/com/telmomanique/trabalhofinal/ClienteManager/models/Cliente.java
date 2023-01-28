package com.telmomanique.trabalhofinal.ClienteManager.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String role;
    private boolean banned;
    private boolean blocked;

}
