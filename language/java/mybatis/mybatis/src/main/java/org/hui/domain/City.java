package org.hui.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class City implements Serializable {
    private Integer id;
    private String name;
    private String countryCode;
    private String district;
    private Integer population;

}
