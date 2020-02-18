package com.derzhavets.repository;

import com.derzhavets.repository.entity.OvertimeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OvertimeRepository extends CrudRepository<OvertimeEntity, Integer> {
    @Query(value = "SELECT * FROM overtime o "
            + "WHERE o.employee_id = :employeeId "
            + "AND (o.start_date BETWEEN :date_from AND :date_to)",
           nativeQuery = true)
    List<OvertimeEntity> findOvertimesForEmployeeBetweenDates(@Param("employeeId") Integer employeeId,
            @Param("date_from") Date dateFrom, @Param("date_to") Date dateTo);

    @Query(value = "SELECT * FROM overtime o "
            + "WHERE o.project_id = :projectId "
            + "AND (o.start_date BETWEEN :date_from AND :date_to)",
           nativeQuery = true)
    List<OvertimeEntity> findOvertimesForProjectBetweenDates(@Param("projectId") Integer projectId,
            @Param("date_from") Date dateFrom, @Param("date_to") Date dateTo);
}