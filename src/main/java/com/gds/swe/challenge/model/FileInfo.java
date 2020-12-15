package com.gds.swe.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @NotBlank(message = "ID is mandatory")
    private String id;

    @Column(name = "login", nullable = false, unique = true)
    @NotBlank(message = "Login is mandatory")
    private String login;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "salary", nullable = false)
    @Min(value = 0, message ="Salary is too low")
    private double salary;


    @Override
    public String toString() {
        return "FileInfo [id=" + id + ", login=" + login + ", name=" + name + ", salary=" + salary + "]";
    }
}
