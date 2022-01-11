package org.hui.java.tutorials;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2022-01-10 21:29
 */
public class EmployeeTest {
    private List<Employee> employees;
    @Before
    public void init() {
        employees = new ArrayList<>();

        employees.add(new Employee("emplyee1", "Dept01", "dev", 3600));
        employees.add(new Employee("emplyee2", "Dept01", "test", 3200));
        employees.add(new Employee("emplyee1", "Dept02", "dev", 3600));
        employees.add(new Employee("emplyee3", "Dept03", "dev", 3000));
        employees.add(new Employee("emplyee3", "Dept03", "dev", 6600));
    }
    @Test
    public void testMap1() {
        Map<String, List<Employee>> deptEmployees = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        for (String dept : deptEmployees.keySet()) {
            System.out.println(dept + ": " + deptEmployees.get(dept));
        }
    }
    @Test
    public void testMap2() {
        Map<String, Integer> deptSalaries = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summingInt(Employee::getSalary)));
        for (String dept : deptSalaries.keySet()) {
            System.out.println(dept + ": " + deptSalaries.get(dept));
        }
    }
    @Test
    public void testMap3() {
        Map<Boolean, List<Employee>> salaryPartition = employees.stream().collect(Collectors.partitioningBy(e -> e.getSalary() >= 3600));
        for (Boolean status : salaryPartition.keySet()) {
            System.out.println(status + ": " + salaryPartition.get(status));
        }
    }
    @Test
    public void testMap4() {
        Map<String, Map<String, List<Employee>>> deptJobs = employees.stream().collect(
                Collectors.groupingBy(Employee::getDepartment,
                        Collectors.groupingBy(Employee::getJob)));
        System.out.println(deptJobs);
    }

    @Test
    public void testFreq() {
//        Map<String, Integer> m = new HashMap<>();
//        Map<String, Integer> m = new TreeMap<>();
        Map<String, Integer> m = new LinkedHashMap<>();

        String[] args = {"if", "it", "is", "to", "be", "it", "is", "up", "to", "me", "to", "delegate"};

        for (String a : args) {
            Integer freq = m.get(a);
            m.put(a, (freq == null) ? 1: freq + 1);
        }

        System.out.println(m.size() + " distinct words");
        System.out.println(m);
    }
    @Test
    public void testKeySet() {
        Map<String, List<Employee>> deptEmployees = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        for (Iterator<String> it = deptEmployees.keySet().iterator(); it.hasNext();) {
            if (it.next().equals("Dept03")) {
                it.remove();
            }
        }
        for (Map.Entry<String, List<Employee>> entry : deptEmployees.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    @Test
    public void testEntryKey() {
        Map<String, List<Employee>> deptEmployees = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        for (Map.Entry<String, List<Employee>> entry : deptEmployees.entrySet()) {
            if (entry.getKey().equals("Dept03")) {
                entry.setValue(new ArrayList<>());
            }
        }
        for (Map.Entry<String, List<Employee>> entry : deptEmployees.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}