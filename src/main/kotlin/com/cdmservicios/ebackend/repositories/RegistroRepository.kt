package com.cdmservicios.ebackend.repositories

import com.cdmservicios.ebackend.models.Registro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegistroRepository: JpaRepository<Registro, Long> {
}