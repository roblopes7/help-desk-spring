package com.udemy.helpdesk.services;

import com.udemy.helpdesk.domain.Chamado;
import com.udemy.helpdesk.domain.Cliente;
import com.udemy.helpdesk.domain.Tecnico;
import com.udemy.helpdesk.domain.enums.Perfil;
import com.udemy.helpdesk.domain.enums.Prioridade;
import com.udemy.helpdesk.domain.enums.Status;
import com.udemy.helpdesk.repositories.ChamadoRepository;
import com.udemy.helpdesk.repositories.ClienteRepository;
import com.udemy.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    TecnicoRepository tecnicoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    public void instanciaDB() {
        Tecnico t1 = new Tecnico(null, "Robson Lopes", "0000000000000", "tecnico@teste.com", "123");
        t1.addPerfis(Perfil.ADMIN);

        Cliente c1 = new Cliente(null, "Cliente teste", "1111111111111111", "cliente@teste.com", "123");

        Chamado chamado = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "chamado 01", "Primeiro Chamado", t1, c1 );

        tecnicoRepository.saveAll(Arrays.asList(t1));
        clienteRepository.saveAll(Arrays.asList(c1));
        chamadoRepository.saveAll(Arrays.asList(chamado));
    }
}
