package com.example.eserciziow7.service;


import com.example.eserciziow7.exception.DispositiviException;
import com.example.eserciziow7.model.Dipendente;
import com.example.eserciziow7.model.Dispositivo;
import com.example.eserciziow7.model.DispositivoRequest;
import com.example.eserciziow7.repository.DispositivoRepository;
import com.example.eserciziow7.exception.DispositiviException;
import com.example.eserciziow7.exception.NotFoundException;
import com.example.eserciziow7.model.Dipendente;
import com.example.eserciziow7.model.Dispositivo;
import com.example.eserciziow7.model.DispositivoRequest;
import com.example.eserciziow7.model.StatoDispositivo;
import com.example.eserciziow7.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private DipendenteService dipendenteService;

    public Page<Dispositivo> getAllDisposit(Pageable pageable){
        return dispositivoRepository.findAll(pageable);
    }
    public Dispositivo getDispositivoId(int id){
        return dispositivoRepository.findById(id).orElseThrow(()->new NotFoundException("Dispositivo assegnato all' id = "+ id+"not found"));
    }
    public Dispositivo saveDispositivo(DispositivoRequest dispositivoRequest)throws DispositiviException {
        Dipendente dipendente=dipendenteService.getDipendenteId(dispositivoRequest.getIdDipendente());
        Dispositivo dispositivo=new Dispositivo( dipendente,dispositivoRequest.getStatoDispositivo(),dispositivoRequest.getTipoDispositivo());
        if( dispositivoRequest.getStatoDispositivo().equals( StatoDispositivo.ASSEGNATO)){
            throw new DispositiviException("Il dispositivo è già stato assegnato");
        } else if (dispositivoRequest.getStatoDispositivo().equals(StatoDispositivo.IN_MANUTENZIONE)) {
            throw new NotFoundException("Il dispositivo non è assegnabile");

        }else if (dispositivoRequest.getStatoDispositivo().equals(StatoDispositivo.DISMESSO)){
            throw new DispositiviException("Il dispositivo è stato dismesso");

        }


        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo refreshDispositivo(int id , DispositivoRequest dispositivoRequest) throws  NotFoundException{
        Dispositivo dispositivo=getDispositivoId(id);
        Dipendente dipendente=dipendenteService.getDipendenteId(dispositivoRequest.getIdDipendente());

        dispositivo.setStatoDispositivo(dispositivoRequest.getStatoDispositivo());
        dispositivo.setDipendente(dipendente);
        dispositivo.setTipoDispositivo(dispositivoRequest.getTipoDispositivo());
        return dispositivoRepository.save(dispositivo);
    }

    public void  delete(int id )throws NotFoundException{
        Dispositivo dispositivo=getDispositivoId(id);
        dispositivoRepository.delete(dispositivo);

    }

}
