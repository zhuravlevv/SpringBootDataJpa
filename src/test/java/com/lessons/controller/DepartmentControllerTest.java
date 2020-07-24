package com.lessons.controller;

import com.lessons.dto.DepartmentDto;
import com.lessons.service.DepartmentService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    public DepartmentService departmentService;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .build();
    }

    @Test
    public void getAll() throws Exception {
        DepartmentDto department = new DepartmentDto(1, "department", new BigDecimal("0"));
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        departmentDtos.add(department);
        when(departmentService.getAll()).thenReturn(departmentDtos);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/department")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("department")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].averageSalary", Matchers.is(0)));
    }

    @Test
    public void getById() throws Exception {
        DepartmentDto department = new DepartmentDto(1, "department", new BigDecimal("0"));

        when(departmentService.getById(any(Integer.class))).thenReturn(department);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("department")))
                .andExpect(MockMvcResultMatchers.jsonPath("averageSalary", Matchers.is(0)));

    }

    @Test
    public void add() throws Exception {

        DepartmentDto departmentDto = new DepartmentDto(1, "department", new BigDecimal("0"));

        when(departmentService.save(any(DepartmentDto.class))).thenReturn(departmentDto);

        String departmentJson = "{\"name\":\"department\"}";

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(departmentJson);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("department")))
                .andExpect(MockMvcResultMatchers.jsonPath("averageSalary", Matchers.is(0)));
    }
}
