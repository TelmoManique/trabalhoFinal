package com.telmomanique.trabalhofinal.ShowMeCliente.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Task {
    private Integer id;
    private String document;
    private String hash;
    private String language;
    private String status;
    private int duration; //sec
    private Timestamp int_date;
    private Timestamp end_date;
    private Integer cliente_id;
}
