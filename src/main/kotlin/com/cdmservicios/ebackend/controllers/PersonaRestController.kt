package com.cdmservicios.ebackend.controllers

import com.cdmservicios.ebackend.models.Persona
import com.cdmservicios.ebackend.services.apis.PersonaServiceAPI
import com.cdmservicios.ebackend.utils.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/persona")
@Api(tags = ["persona"])
class PersonaRestController(serviceAPI: PersonaServiceAPI) : GenericRestController<Persona, String>(serviceAPI)