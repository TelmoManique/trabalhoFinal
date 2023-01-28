package com.telmomanique.trabalhofinal.TheLanguageFinder.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;


@Data
public class Cliente {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private boolean banned;
    private boolean blocked;

    @JsonIgnore
    private List<Task> tasks;

}
