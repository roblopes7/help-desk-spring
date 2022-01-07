package com.udemy.helpdesk.resources;

import com.udemy.helpdesk.domain.Chamado;
import com.udemy.helpdesk.domain.dto.ChamadoDTO;
import com.udemy.helpdesk.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {

    @Autowired
    ChamadoService chamadoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        Chamado obj = chamadoService.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }

    @GetMapping
    public  ResponseEntity<List<ChamadoDTO>> findAll() {
        List<Chamado> objs = chamadoService.findAll();
        return ResponseEntity.ok().body(objs.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList()));
    }
}
