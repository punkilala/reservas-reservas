package com.reservas.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VuelosResponseDto {
	private int idVuelo;
	private String company;
	private String fecha;
	private double precio;
	private int plazas;
}
