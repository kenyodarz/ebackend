package com.cdmservicios.ebackend.services

import com.cdmservicios.ebackend.models.Persona
import com.cdmservicios.ebackend.repositories.PersonaRepository
import com.cdmservicios.ebackend.services.apis.PersonaServiceAPI
import com.cdmservicios.ebackend.utils.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class PersonaServiceImpl(repository: PersonaRepository) : GenericServiceImpl<Persona, String>(), PersonaServiceAPI {

    private var repository: PersonaRepository?=null

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Persona, String> {
        return repository!!
    }
}