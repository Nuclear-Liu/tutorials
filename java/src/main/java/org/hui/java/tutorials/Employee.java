package org.hui.java.tutorials;

import java.util.Objects;

/**
 * @author Hui.Liu
 * @since 2022-01-10 21:06
 */
public class Employee {
    private String name;
    private String department;
    private String job;
    private int salary;

    public Employee() {
    }

    public Employee(String name, String department, String job, int salary) {
        this.name = name;
        this.department = department;
        this.job = job;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getSalary() == employee.getSalary() && Objects.equals(getName(), employee.getName()) && Objects.equals(getDepartment(), employee.getDepartment()) && Objects.equals(getJob(), employee.getJob());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDepartment(), getJob(), getSalary());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                '}';
    }
}
