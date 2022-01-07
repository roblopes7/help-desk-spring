package com.udemy.helpdesk.resources;

import com.udemy.helpdesk.domain.Chamado;
import com.udemy.helpdesk.domain.dto.ChamadoDTO;
import com.udemy.helpdesk.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO dto){
        Chamado obj = chamadoService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO dto){
        Chamado newObj = chamadoService.update(id, dto);
        return ResponseEntity.ok().body(new ChamadoDTO(newObj));
    }
}
