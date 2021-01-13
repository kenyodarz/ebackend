package com.cdmservicios.ebackend.controllers

import com.cdmservicios.ebackend.core.CalculoHoras
import com.cdmservicios.ebackend.models.Persona
import com.cdmservicios.ebackend.models.Proyecto
import com.cdmservicios.ebackend.models.Registro
import com.cdmservicios.ebackend.services.apis.PersonaServiceAPI
import com.cdmservicios.ebackend.services.apis.ProyectoServiceAPI
import com.cdmservicios.ebackend.services.apis.RegistroServiceAPI
import com.cdmservicios.ebackend.utils.shared.GenericRestController
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/registro")
@Api(tags = ["registro"])
class RegistroRestController(serviceAPI: RegistroServiceAPI) : GenericRestController<Registro, Long>(serviceAPI) {

    @Autowired
    private val personaServiceAPI: PersonaServiceAPI? = null

    @Autowired
    private val proyectoServiceAPI: ProyectoServiceAPI? = null

    override fun save(entity: Registro, result: BindingResult): ResponseEntity<*> {
        val calculoHoras = CalculoHoras()
        calculoHoras.calcularHoras(entity.horaEntrada!!, entity.horaSalida!!, entity.festivo)
        entity.horaOrdinaria = calculoHoras.horasOrdinarias
        entity.recargoNocturno = calculoHoras.recargosNocturnos
        entity.horaExtra = calculoHoras.horasExtrasOrdinarias
        entity.horaExtraNocturna = calculoHoras.horasExtrasNocturnas
        entity.horaExtraFestiva = calculoHoras.horasExtrasOrdinariasFestivas
        entity.horaExtraFestivaNocturna = calculoHoras.horasExtrasNocturnasFestivas
        val persona: Persona = personaServiceAPI!!.getOne(entity.persona!!.cedula!!)
        entity.persona = persona
        calculoHoras.calcularSueldo(persona.salario!!)
        entity.salarioSinPrestaciones = calculoHoras.salarioSinPrestaciones
        entity.salarioConPrestaciones = calculoHoras.salarioConPrestaciones
        val proyecto: Proyecto = proyectoServiceAPI!!.getOne(entity.proyecto!!.idProyecto!!)
        entity.proyecto = proyecto
        return super.save(entity, result)
    }

    @PostMapping("/segundo/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun segundoRegistro(@PathVariable id: Long, @RequestBody registro: Registro): ResponseEntity<Registro?>? {
        val calculoHoras = CalculoHoras()
        val primerRegistro: Registro = serviceAPI.getOne(id)
        calculoHoras.calcularHorasSegundoRegistro(
            registro.horaEntrada!!,
            registro.horaSalida!!,
            registro.festivo,
            primerRegistro
        )
        registro.horaOrdinaria = calculoHoras.horasOrdinarias
        registro.recargoNocturno = calculoHoras.recargosNocturnos
        registro.horaExtra = calculoHoras.horasExtrasOrdinarias
        registro.horaExtraNocturna = calculoHoras.horasExtrasNocturnas
        registro.horaExtraFestiva = calculoHoras.horasExtrasOrdinariasFestivas
        registro.horaExtraFestivaNocturna = calculoHoras.horasExtrasNocturnasFestivas
        val persona: Persona = personaServiceAPI!!.getOne(registro.persona!!.cedula!!)
        registro.persona = persona
        calculoHoras.calcularSueldo(persona.salario!!)
        registro.salarioSinPrestaciones = calculoHoras.salarioSinPrestaciones
        registro.salarioConPrestaciones = calculoHoras.salarioConPrestaciones
        val proyecto: Proyecto = proyectoServiceAPI!!.getOne(registro.proyecto!!.idProyecto!!)
        registro.proyecto = proyecto
        val obj: Registro = serviceAPI.save(registro)
        return ResponseEntity(obj, HttpStatus.OK)
    }
}