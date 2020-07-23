package com.lessons.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {

    private Integer id;
    private String name;
    private BigDecimal averageSalary;

}
