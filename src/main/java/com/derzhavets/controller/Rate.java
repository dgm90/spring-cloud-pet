package com.derzhavets.controller;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rates")
public class Rate {
    @Id
    public String id;
    public Double rate;
    public Integer projectId;
}
