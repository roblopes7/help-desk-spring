package com.udemy.helpdesk.services;

import com.udemy.helpdesk.domain.Pessoa;
import com.udemy.helpdesk.domain.Tecnico;
import com.udemy.helpdesk.domain.dto.TecnicoDTO;
import com.udemy.helpdesk.repositories.PessoaRepository;
import com.udemy.helpdesk.repositories.TecnicoRepository;
import com.udemy.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.udemy.helpdesk.services.exceptions.ObjectNotFounException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    TecnicoRepository tecnicoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFounException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO dto) {
        dto.setId(null);
        validaPorCpfEEmail(dto);
        dto.setSenha(encoder.encode(dto.getSenha()));
        Tecnico tecnico = new Tecnico(dto);
        return tecnicoRepository.save(tecnico);
    }

    private void validaPorCpfEEmail(TecnicoDTO dto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(dto.getCpf());
        if(obj.isPresent() && obj.get().getId() != dto.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(dto.getEmail());
        if(obj.isPresent() && obj.get().getId() != dto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }

    }


    public Tecnico update(Integer id, TecnicoDTO dto) {
        dto.setId(id);
        Tecnico oldTecnico = findById(id);
        if(!dto.getSenha().equals(oldTecnico.getSenha())) {
            dto.setSenha(encoder.encode(dto.getSenha()));
        }
        validaPorCpfEEmail(dto);
        oldTecnico = new Tecnico(dto);
        return tecnicoRepository.save(oldTecnico);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço, não pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }
}
