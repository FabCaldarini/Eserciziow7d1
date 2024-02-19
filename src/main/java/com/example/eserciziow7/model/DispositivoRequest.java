package com.example.eserciziow7.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DispositivoRequest {
    @NotNull(message = "stato dispositivo da inserire")
    private StatoDispositivo statoDispositivo;
    @NotNull(message = "tipo dispositivo da inserire")
    private TipoDispositivo tipoDispositivo;
    @NotNull(message = "dipendente da inserire")
    private Integer idDipendente;


}
