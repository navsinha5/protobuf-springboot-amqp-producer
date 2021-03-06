package com.assignment.gateway.model;

import javax.validation.constraints.*;

public class PersonUpdateReq {
    @Size(min = 2, max = 40)
    private String name;
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
    private String dob;
    @Pattern(regexp = "\\d+\\.?\\d*")
    private String salary;
    @Min(value = 1, message= "age can't be less than zero")
    @Max(value = 100, message = "age can't be greater than 100")
    private Integer age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
