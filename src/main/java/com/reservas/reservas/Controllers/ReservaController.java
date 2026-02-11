package com.reservas.reservas.Controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.reservas.dto.ReservaRequestDto;
import com.reservas.reservas.dto.ReservaResponseDto;
import com.reservas.reservas.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservaController {
	private final ReservaService reservaService;
	
	@GetMapping(value = "/reservas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReservaResponseDto>> allReservas(){
		return ResponseEntity.ok(reservaService.allReservas());
	}
	
	@PostMapping(value = "/reserva", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReservaResponseDto> crearReserva(@RequestBody ReservaRequestDto reservaRequestDto){
		return ResponseEntity.ok(reservaService.crearReseva(reservaRequestDto));
		
	}

}
