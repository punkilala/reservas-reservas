package com.reservas.reservas.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.reservas.reservas.dto.HotelResponseDto;
import com.reservas.reservas.dto.ReservaRequestDto;
import com.reservas.reservas.dto.ReservaResponseDto;
import com.reservas.reservas.dto.VuelosRequestDto;
import com.reservas.reservas.dto.VuelosResponseDto;
import com.reservas.reservas.entity.ReservaEntity;
import com.reservas.reservas.mappers.ReservaMapper;
import com.reservas.reservas.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService{
	private final ReservaMapper reservaMapper;
	private final ReservaRepository reservaRepository;
	private final WebClient.Builder webClient;
	
	@Transactional(readOnly = true)
	@Override
	public List<ReservaResponseDto> allReservas() {
		List<ReservaEntity> reservasBdd = reservaRepository.findAll();
		List<ReservaResponseDto> resultDto = reservaMapper.toReservasDto(reservasBdd);
		return resultDto;
	}

	@Override
	@Transactional
	public ReservaResponseDto crearReseva(ReservaRequestDto reservaRequestDto) {
		//obtener hoteles disponibles
		List<HotelResponseDto> hoteles = webClient.build()
				.get()
			    .uri("http://reservas-hoteles/hoteles")
			    .retrieve()
			    .onStatus(HttpStatusCode::isError,
			        response -> Mono.error(
			            new RuntimeException(
			                "Error reserva-hoteles | status=" + response.statusCode()
			            )
			        )
			    )
			    .bodyToFlux(HotelResponseDto.class)
			    .filter(h -> h.getPrecio() >= 10.0)
			    .collectList()
			    .block();
		if(hoteles.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay hoteles disponibles");
		}
		
		
		//obtener vuelos disponibles
		List<VuelosResponseDto> vuelos = webClient.build()
				.get()
			    .uri("http://reservas-vuelos/vuelos/disponibles/" + reservaRequestDto.getPlazas())
			    .retrieve()
			    .onStatus(HttpStatusCode::isError,
			        response -> Mono.error(
			            new RuntimeException(
			                "Error reservas-vuelos| status=" + response.statusCode()
			            )
			        )
			    )
			    .bodyToFlux(VuelosResponseDto.class)
			    .collectList()
			    .block();
		
		if(vuelos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay vuelos disponibles");
		}
		
		VuelosResponseDto vueloReservado = vuelos.get(0);
		
		//actualizsr vuelos
		VuelosRequestDto vueloRequestDto = new VuelosRequestDto(reservaRequestDto.getPlazas());
		webClient.build()
		.patch()
		.uri("http://reservas-vuelos/vuelos/actualiza/" + vueloReservado.getIdVuelo())
		.bodyValue(vueloRequestDto)
		.retrieve()
		.onStatus(HttpStatusCode::isError,
	            response -> Mono.error(
	                    new RuntimeException(
	                        "Error actualizando vuelo | status=" + response.statusCode()
	                    )
	                )
	            )
		.toBodilessEntity()
		.block();
		
		//confirmar Reserva;
		ReservaEntity reservaEntity = new ReservaEntity();
		reservaEntity.setDni(reservaRequestDto.getDni());
		reservaEntity.setNombre(reservaRequestDto.getNombre());
		reservaEntity.setHotel(hoteles.get(0).getIdHotel());
		reservaEntity.setVuelo(vueloReservado.getIdVuelo());
		
		reservaRepository.save(reservaEntity);	
			
		
		return reservaMapper.toReservaDto(reservaEntity);
	}

}
