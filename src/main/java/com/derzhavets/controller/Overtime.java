package com.derzhavets.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Overtime {
    private Integer id;
    private Integer projectId;
    private Integer hours;
}
