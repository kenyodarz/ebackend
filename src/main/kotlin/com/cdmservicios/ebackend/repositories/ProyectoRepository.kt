package com.cdmservicios.ebackend.repositories

import com.cdmservicios.ebackend.models.Proyecto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProyectoRepository: JpaRepository<Proyecto, String> {
}