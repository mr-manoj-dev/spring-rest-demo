package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class EmpRQ {
    @NotNull(message = "name shouldn't be null")
    private String name;
    @NotNull(message = "joining date shouldn't be null")
    private String joiningDate;
    @Email(message = "invalid email address")
    private String email;
}
