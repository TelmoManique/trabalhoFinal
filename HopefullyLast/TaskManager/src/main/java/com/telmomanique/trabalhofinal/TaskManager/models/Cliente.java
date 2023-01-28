package com.telmomanique.trabalhofinal.TaskManager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name= "Cliente")
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

    @JsonIgnore
    @OneToMany(mappedBy = "cliente_id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;

}
