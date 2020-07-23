package com.lessons.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private Integer id;
    private String name;
    private Integer departmentId;
    private BigDecimal salary;

}
