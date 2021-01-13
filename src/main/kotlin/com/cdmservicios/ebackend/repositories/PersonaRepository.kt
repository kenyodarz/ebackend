package com.cdmservicios.ebackend.repositories

import com.cdmservicios.ebackend.models.Persona
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonaRepository: JpaRepository<Persona, String> {
}