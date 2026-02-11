package com.reservas.reservas.service;

import java.util.List;

import com.reservas.reservas.dto.ReservaRequestDto;
import com.reservas.reservas.dto.ReservaResponseDto;

public interface ReservaService {
	List<ReservaResponseDto> allReservas();
	ReservaResponseDto crearReseva(ReservaRequestDto reservaRequestDto);

}
