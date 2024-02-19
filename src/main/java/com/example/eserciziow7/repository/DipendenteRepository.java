package com.example.eserciziow7.repository;


import com.example.eserciziow7.model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DipendenteRepository extends  JpaRepository<Dipendente, Integer> {
    Dipendente findByEmail(String email);
}