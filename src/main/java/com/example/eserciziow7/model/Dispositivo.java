package com.example.eserciziow7.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;
    @Column(name = "stato_dispositivo")
    @Enumerated(EnumType.STRING)
    private  StatoDispositivo statoDispositivo;
    @Column(name = "tipo_dispositivo")
    @Enumerated(EnumType.STRING)
    private TipoDispositivo tipoDispositivo;

    public Dispositivo(Dipendente dipendente, StatoDispositivo statoDispositivo,TipoDispositivo tipoDispositivo) {
        this.dipendente = dipendente;
        this.statoDispositivo = statoDispositivo;
        this.tipoDispositivo=tipoDispositivo;
    }
    public Dispositivo(){}

}
