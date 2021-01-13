package com.cdmservicios.ebackend.services

import com.cdmservicios.ebackend.models.Proyecto
import com.cdmservicios.ebackend.repositories.ProyectoRepository
import com.cdmservicios.ebackend.services.apis.ProyectoServiceAPI
import com.cdmservicios.ebackend.utils.shared.GenericServiceImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ProyectoServiceImpl(repository: ProyectoRepository) : GenericServiceImpl<Proyecto, String>(), ProyectoServiceAPI {

    private var repository: ProyectoRepository? = null

    init {
        repository.also { this.repository = it }
    }

    override fun getRepository(): JpaRepository<Proyecto, String> {
        return repository!!
    }


}