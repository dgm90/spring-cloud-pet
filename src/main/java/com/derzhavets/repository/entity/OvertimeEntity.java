package com.derzhavets.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "overtime")
public class OvertimeEntity extends BaseEntity {
    @NotNull
    private Date startDate;

    @NotNull
    private Double hours;

    @ManyToOne
    private EmployeeEntity employee;

    @ManyToOne
    private ProjectEntity project;
}