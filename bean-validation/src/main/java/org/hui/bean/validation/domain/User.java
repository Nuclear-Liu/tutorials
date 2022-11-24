package org.hui.bean.validation.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class User {
    private long id;

    @NotNull(message = "not null")
    @Email(message = "email must is email type")
    private String name;

    @Pattern(regexp = "^[A-Z]$/", message = "首字母必须是 A-Z")
    private String city;

    @NotEmpty(message = "not null")
    private String sex;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
