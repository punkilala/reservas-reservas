package com.reservas.reservas.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.reservas.reservas.config.ConfiguracionMapper;
import com.reservas.reservas.dto.ReservaResponseDto;
import com.reservas.reservas.entity.ReservaEntity;

@Mapper(config = ConfiguracionMapper.class)
public interface ReservaMapper {
	List<ReservaResponseDto> toReservasDto(List<ReservaEntity> reservasEntity);
	ReservaResponseDto toReservaDto (ReservaEntity reservaEntity);

}
