package com.reservas.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseDto {
	private int idHotel;
	private String nombre;
	private int categoria;
	private double precio;
	private boolean disponible;

}
