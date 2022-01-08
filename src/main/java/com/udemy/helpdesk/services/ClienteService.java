package com.udemy.helpdesk.services;

import com.udemy.helpdesk.domain.Pessoa;
import com.udemy.helpdesk.domain.Cliente;
import com.udemy.helpdesk.domain.dto.ClienteDTO;
import com.udemy.helpdesk.repositories.PessoaRepository;
import com.udemy.helpdesk.repositories.ClienteRepository;
import com.udemy.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.udemy.helpdesk.services.exceptions.ObjectNotFounException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFounException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente create(ClienteDTO dto) {
        dto.setId(null);
        validaPorCpfEEmail(dto);
        dto.setSenha(encoder.encode(dto.getSenha()));
        Cliente cliente = new Cliente(dto);
        return clienteRepository.save(cliente);
    }

    private void validaPorCpfEEmail(ClienteDTO dto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(dto.getCpf());
        if(obj.isPresent() && obj.get().getId() != dto.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(dto.getEmail());
        if(obj.isPresent() && obj.get().getId() != dto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }

    }


    public Cliente update(Integer id, ClienteDTO dto) {
        dto.setId(id);
        Cliente oldCliente = findById(id);
        validaPorCpfEEmail(dto);
        oldCliente = new Cliente(dto);
        return clienteRepository.save(oldCliente);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço, não pode ser deletado!");
        }
        clienteRepository.deleteById(id);
    }
}
