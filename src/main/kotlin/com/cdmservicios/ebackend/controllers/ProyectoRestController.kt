package com.cdmservicios.ebackend.controllers

import com.cdmservicios.ebackend.models.Proyecto
import com.cdmservicios.ebackend.services.apis.ProyectoServiceAPI
import com.cdmservicios.ebackend.utils.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/proyecto")
@Api(tags = ["proyecto"])
class ProyectoRestController(serviceAPI: ProyectoServiceAPI) : GenericRestController<Proyecto, String>(serviceAPI)