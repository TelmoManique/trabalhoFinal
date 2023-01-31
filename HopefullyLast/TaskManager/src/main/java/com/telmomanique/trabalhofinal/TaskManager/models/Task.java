package com.telmomanique.trabalhofinal.TaskManager.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;

@Entity(name= "Task")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String document;
    private String hash;
    private String language;
    private String status;
    private Duration duration;
    private String type;
    private Timestamp int_date;
    private Timestamp end_date;

    @ManyToOne(fetch =  FetchType.EAGER, optional = false)
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
    private Cliente cliente_id;
}
