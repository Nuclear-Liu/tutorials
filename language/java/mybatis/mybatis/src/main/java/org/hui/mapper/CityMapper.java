package org.hui.mapper;

import org.hui.domain.City;

import java.util.List;

public interface CityMapper {
    List<City> selectCities();
    City selectCityById(Integer id);
}
