package com.example.eserciziow7.controller;

import com.example.eserciziow7.exception.BadRequestException;
import com.example.eserciziow7.model.Dipendente;
import com.example.eserciziow7.model.DipendenteRequest;
import com.example.eserciziow7.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/dipendenti")
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return dipendenteService.saveDipend(dipendenteRequest);
    }

    @GetMapping("/dipendenti")
    public Page<Dipendente> getAll(Pageable pageable) {
        return dipendenteService.getAllDipend(pageable);
    }

    @GetMapping("/dipendenti/{id}")
    public Dipendente getDipendenteById(@PathVariable int id) {
        return dipendenteService.getDipendenteId(id);
    }

    @PutMapping("/dipendenti/{id}")
    public Dipendente updateDipendente(@PathVariable int id, @RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return dipendenteService.refreshDipendente(id, dipendenteRequest);
    }

    @PatchMapping("/dipendenti/{id}/upload")
    public Dipendente uploadAvatar(@PathVariable int id, @RequestParam("upload") MultipartFile file) throws BadRequestException, IOException {
        if (file.isEmpty()) {
            throw new BadRequestException("File non valido");
        }

        // Salva il file sul server
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get("path/to/upload/directory/" + fileName); // Sostituisci con il percorso della directory di caricamento sul tuo server
        Files.copy(file.getInputStream(), uploadPath);

        // Aggiorna il percorso del file nel Dipendente e salva nel database
        String fileUrl = "/uploads/" + fileName; // Sostituisci con il percorso del file rispetto alla radice del server
        return dipendenteService.uploadAvatar(id, fileUrl);
    }

    @DeleteMapping("/dipendenti/{id}")
    public void deleteDipendente(@PathVariable int id) {
        dipendenteService.deleteDipendente(id);
    }
}
