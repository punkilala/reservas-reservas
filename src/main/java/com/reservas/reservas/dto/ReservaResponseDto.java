package com.reservas.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDto {
	private int idReserva;
	private String nombre;
	private String  dni;
	private int hotel;
	private int vuelo;
}
