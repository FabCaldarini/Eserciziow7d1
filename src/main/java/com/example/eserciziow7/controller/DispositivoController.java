package com.example.eserciziow7.controller;


import com.example.eserciziow7.exception.BadRequestException;
import com.example.eserciziow7.model.Dispositivo;
import com.example.eserciziow7.model.DispositivoRequest;
import com.example.eserciziow7.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping("/dispositivi")
    public Dispositivo saveDispositivo(@RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return dispositivoService.saveDispositivo(dispositivoRequest);
    }

    @GetMapping("/dispositivi")
    public Page<Dispositivo> getAll(Pageable pageable) {
        return dispositivoService.getAllDisposit(pageable);
    }

    @GetMapping("/dispositivi/{id}")
    public Dispositivo getDispositivoById(@PathVariable int id) {
        return dispositivoService.getDispositivoId(id);
    }

    @PutMapping("/dispositivi/{id}")
    public Dispositivo updateDispositivo(@PathVariable int id, @RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return dispositivoService.refreshDispositivo(id, dispositivoRequest);
    }

    @DeleteMapping("/dispositivi/{id}")
    public void deleteDispositivo(@PathVariable int id) {
        dispositivoService.delete(id);
    }
}

