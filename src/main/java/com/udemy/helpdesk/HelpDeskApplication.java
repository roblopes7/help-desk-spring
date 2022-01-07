package com.udemy.helpdesk;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class HelpDeskApplication implements CommandLineRunner {

	@Autowired
	TecnicoRepository tecnicoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpDeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico t1 = new Tecnico(null, "Robson Lopes", "0000000000000", "tecnico@teste.com", "123");
		t1.addPerfis(Perfil.ADMIN);

		Cliente c1 = new Cliente(null, "Cliente teste", "1111111111111111", "cliente@teste.com", "123");

		Chamado chamado = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "chamado 01", "Primeiro Chamado", t1, c1 );

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		chamadoRepository.saveAll(Arrays.asList(chamado));

	}
}
