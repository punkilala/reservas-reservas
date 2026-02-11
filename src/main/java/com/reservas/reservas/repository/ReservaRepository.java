package com.reservas.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservas.reservas.entity.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Integer>{

}
