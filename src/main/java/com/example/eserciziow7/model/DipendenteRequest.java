package com.example.eserciziow7.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DipendenteRequest {
    @NotNull(message = "username da inserire")
    @NotEmpty(message = "username non inserito")
    private String username;
    @NotNull(message = "nome da inserire")
    @NotEmpty(message = "nome non inserito")
    private String nome;
    @NotNull(message = "cognome da inserire")
    @NotEmpty(message = "cognome non inserito")
    private String cognome;
    @Email(message = "email da inserire")
    private  String email;
    private List < Dispositivo> dispositivo;

}
