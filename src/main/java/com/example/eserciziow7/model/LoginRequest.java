package com.example.eserciziow7.model;

public class LoginRequest {
    private String email;
    private String password;

    // Costruttore vuoto (necessario per deserializzazione JSON)
    public LoginRequest() {
    }

    // Costruttore con parametri
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Metodi getter e setter per email e password
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

