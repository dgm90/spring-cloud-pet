package com.derzhavets.controller;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "bonus")
public class Bonus {
    @Id
    public String id;
    public Double bonus;
    public Integer overtimeId;

    public Bonus(Double bonus, Integer overtimeId) {
        this.bonus = bonus;
        this.overtimeId = overtimeId;
    }
}
