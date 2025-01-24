package com.infominez.catalog.auth.repository;

import com.infominez.catalog.auth.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Object> findByCountryName(String countryName);
}
