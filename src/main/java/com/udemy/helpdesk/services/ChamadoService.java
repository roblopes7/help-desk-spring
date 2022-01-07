package com.udemy.helpdesk.services;

import com.udemy.helpdesk.domain.Chamado;
import com.udemy.helpdesk.repositories.ChamadoRepository;
import com.udemy.helpdesk.services.exceptions.ObjectNotFounException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    public Chamado findById(Integer id) {
        Optional<Chamado> chamado = chamadoRepository.findById(id);
        return chamado.orElseThrow(() -> new ObjectNotFounException("Chamado não encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

}
