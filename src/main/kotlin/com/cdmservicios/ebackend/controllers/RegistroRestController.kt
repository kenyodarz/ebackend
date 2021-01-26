package com.cdmservicios.ebackend.controllers

import com.cdmservicios.ebackend.core.CalculoHoras
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
        with(entity) {
            horaOrdinaria = calculoHoras.horasOrdinarias
            recargoNocturno = calculoHoras.recargosNocturnos
            horaExtra = calculoHoras.horasExtrasOrdinarias
            horaExtraNocturna = calculoHoras.horasExtrasNocturnas
            horaExtraFestiva = calculoHoras.horasExtrasOrdinariasFestivas
            horaExtraFestivaNocturna = calculoHoras.horasExtrasNocturnasFestivas
            persona = personaServiceAPI!!.getOne(entity.persona!!.cedula!!)
            calculoHoras.calcularSueldo(persona!!.salario!!)
            salarioSinPrestaciones = calculoHoras.salarioSinPrestaciones
            salarioConPrestaciones = calculoHoras.salarioConPrestaciones
            proyecto = proyectoServiceAPI!!.getOne(entity.proyecto!!.idProyecto!!)
        }
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
        with(receiver = registro) {
            calculoHoras.horasOrdinarias.also { horaOrdinaria = it }
            calculoHoras.recargosNocturnos.also { recargoNocturno = it }
            calculoHoras.horasExtrasOrdinarias.also { horaExtra = it }
            calculoHoras.horasExtrasNocturnas.also { horaExtraNocturna = it }
            calculoHoras.horasExtrasOrdinariasFestivas.also { horaExtraFestiva = it }
            calculoHoras.horasExtrasNocturnasFestivas.also { horaExtraFestivaNocturna = it }
            personaServiceAPI!!.getOne(registro.persona!!.cedula!!).also { persona = it }
            calculoHoras.calcularSueldo(persona!!.salario!!)
            calculoHoras.salarioSinPrestaciones.also { salarioSinPrestaciones = it }
            calculoHoras.salarioConPrestaciones.also { salarioConPrestaciones = it }
            proyectoServiceAPI!!.getOne(registro.proyecto!!.idProyecto!!).also { proyecto = it }
        }
        val obj: Registro = serviceAPI.save(registro)
        return ResponseEntity(obj, HttpStatus.OK)
    }
}