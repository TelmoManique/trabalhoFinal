package com.telmomanique.trabalhofinal.ShowMeCliente.models;

import lombok.Data;

//import javax.persistence.*

//@Entity
@Data
public class Cliente {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

     */
    private Integer id;
    private String username;
    private String password;
    private String role;
    private boolean banned;
    private boolean blocked;

}
