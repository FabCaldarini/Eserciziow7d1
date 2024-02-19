package com.example.eserciziow7.service;

import com.example.eserciziow7.exception.NotFoundException;
import com.example.eserciziow7.model.Dipendente;
import com.example.eserciziow7.model.DipendenteRequest;
import com.example.eserciziow7.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService implements UserDetailsService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Dipendente dipendente = dipendenteRepository.findByEmail(email);
        if (dipendente == null) {
            throw new UsernameNotFoundException("Utente non trovato con email: " + email);
        }
        // Restituisci un UserDetails con la password vuota
        return new org.springframework.security.core.userdetails.User(dipendente.getEmail(), "", null);
    }

    public UserDetails authenticate(String email, String password) {
        // Cerca il dipendente per email
        Dipendente dipendente = dipendenteRepository.findByEmail(email);
        if (dipendente != null && isValidPassword(dipendente, password)) {
            // Se l'email e la password corrispondono, restituisci un UserDetails
            return new org.springframework.security.core.userdetails.User(dipendente.getEmail(), "", null);
        }
        return null; // Se le credenziali non corrispondono, restituisci null
    }

    private boolean isValidPassword(Dipendente dipendente, String password) {
        // Implementa la logica per verificare se la password è valida
        // Ad esempio, puoi confrontare la password fornita con quella memorizzata nel dipendente
        // o utilizzare un meccanismo di hashing per confrontare le password
        return dipendente.getPassword().equals(password); // Esempio semplice di confronto delle password
    }

    public Page<Dipendente> getAllDipend(Pageable pageable) {
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente getDipendenteId(int id) {
        return dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Dipendente assegnato all' id= " + id + " non trovato"));
    }

    public Dipendente saveDipend(DipendenteRequest dipendenteRequest) throws NotFoundException {
        Dipendente dipendente = new Dipendente(dipendenteRequest.getNome(), dipendenteRequest.getCognome(), dipendenteRequest.getUsername(), dipendenteRequest.getEmail());
        sendMail(dipendente.getEmail());
        return dipendenteRepository.save(dipendente);
    }

    public void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione");
        message.setText("Registrazione confermata");
        javaMailSender.send(message);
    }

    public Dipendente refreshDipendente(int id, DipendenteRequest dipendenteRequest) throws NotFoundException {
        Dipendente dipendente = getDipendenteId(id);
        dipendente.setDispositivos(dipendenteRequest.getDispositivo());
        dipendente.setEmail(dipendenteRequest.getEmail());
        dipendente.setNome(dipendenteRequest.getNome());
        dipendente.setCognome(dipendenteRequest.getCognome());
        dipendente.setUsername(dipendenteRequest.getUsername());
        return dipendenteRepository.save(dipendente);
    }

    public Dipendente uploadAvatar(int id, String url) throws NotFoundException {
        Dipendente dipendente = getDipendenteId(id);
        dipendente.setAvatar(url);
        return dipendenteRepository.save(dipendente);
    }

    public void deleteDipendente(int id) throws NotFoundException {
        Dipendente dipendente = getDipendenteId(id);
        dipendenteRepository.delete(dipendente);
    }
}



