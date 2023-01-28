package com.telmomanique.trabalhofinal.TheLanguageFinder.models;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Task {

    private Integer id;
    private String document;
    private String hash;
    private String language;
    private String status;
    private int duration;
    private Timestamp int_date;
    private Timestamp end_date;

    private Cliente cliente_id;
}
