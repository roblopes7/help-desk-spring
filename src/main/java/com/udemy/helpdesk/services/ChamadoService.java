package com.udemy.helpdesk.services;

import com.udemy.helpdesk.domain.Chamado;
import com.udemy.helpdesk.domain.Cliente;
import com.udemy.helpdesk.domain.Tecnico;
import com.udemy.helpdesk.domain.dto.ChamadoDTO;
import com.udemy.helpdesk.domain.enums.Prioridade;
import com.udemy.helpdesk.domain.enums.Status;
import com.udemy.helpdesk.repositories.ChamadoRepository;
import com.udemy.helpdesk.services.exceptions.ObjectNotFounException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TecnicoService tecnicoService;

    public Chamado findById(Integer id) {
        Optional<Chamado> chamado = chamadoRepository.findById(id);
        return chamado.orElseThrow(() -> new ObjectNotFounException("Chamado não encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    public Chamado create(ChamadoDTO dto) {
        return chamadoRepository.save(newChamado(dto));
    }

    private Chamado newChamado(ChamadoDTO dto) {
        Tecnico tecnico = tecnicoService.findById(dto.getTecnico());
        Cliente cliente = clienteService.findById(dto.getCliente());

        Chamado chamado = new Chamado();
        if(dto != null) {
            chamado.setId(dto.getId());
        }

        if(dto.getStatus().equals(Status.ENCERRADO.getCodigo())) {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(dto.getPrioridade()));
        chamado.setStatus(Status.toEnum(dto.getStatus()));
        chamado.setTitulo(dto.getTitulo());
        chamado.setObservacoes(dto.getObservacoes());

        return chamado;
    }

    public Chamado update(Integer id, ChamadoDTO dto) {
        dto.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(dto);
        return chamadoRepository.save(oldObj);
    }
}
