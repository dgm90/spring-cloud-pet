package com.derzhavets.controller.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class Overtime {
    private Integer id;
    private Integer employeeId;
    private Integer projectId;
    private Date startDate;
    private Double hours;
}
