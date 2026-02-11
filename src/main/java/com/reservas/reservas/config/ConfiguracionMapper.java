package com.reservas.reservas.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring", 
							unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ConfiguracionMapper {
	
}
