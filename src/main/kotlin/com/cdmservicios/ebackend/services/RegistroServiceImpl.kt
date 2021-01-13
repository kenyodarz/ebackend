package com.cdmservicios.ebackend.services

import com.cdmservicios.ebackend.models.Registro
import com.cdmservicios.ebackend.repositories.RegistroRepository
import com.cdmservicios.ebackend.services.apis.RegistroServiceAPI
import com.cdmservicios.ebackend.utils.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class RegistroServiceImpl(repository: RegistroRepository) : GenericServiceImpl<Registro, Long>(), RegistroServiceAPI {

    private var repository: RegistroRepository? = null

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Registro, Long> {
        return repository!!
    }
}