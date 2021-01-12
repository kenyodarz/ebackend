package com.cdmservicios.ebackend.utils.shared

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.util.*
import java.util.function.Consumer
import javax.validation.Valid


@RestController
abstract class GenericRestController<T, ID : Serializable> @Autowired constructor(private var serviceAPI: GenericServiceAPI<T, ID>?) {


    fun validar(result: BindingResult): ResponseEntity<*>? {
        val errores: MutableMap<String, Any> = HashMap()
        result.fieldErrors.forEach(Consumer { err: FieldError -> errores[err.field] = " El campo " + err.field + " " + err.defaultMessage })
        return ResponseEntity.badRequest().body<Map<String, Any>>(errores)
    }

    @GetMapping("/all")
    @ApiOperation(value = "Listar Entidades", notes = "servicio para listar todas las Entidades")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Entidades Listadas Correctamente"),
        ApiResponse(code = 401, message = "Usuario No Autorizado"),
        ApiResponse(code = 403, message = "Solicitud prohibida por el servidor"),
        ApiResponse(code = 404, message = "Entidad no encontrada")])
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun getAll(): List<T?>? {
        return serviceAPI!!.getAll()
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener una Entidad", notes = "servicio para obtener una Entidad")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Entidad encontrada correctamente"),
        ApiResponse(code = 401, message = "Usuario No Autorizado"),
        ApiResponse(code = 403, message = "Solicitud prohibida por el servidor"),
        ApiResponse(code = 404, message = "Entidad no encontrada")])
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun find(@PathVariable id: ID): ResponseEntity<*>? {
        val entity: T = serviceAPI!!.getOne(id) ?: return ResponseEntity.notFound().build<Any>()
        return ResponseEntity.ok<Any>(entity!!)
    }

    @PostMapping("/save")
    @ApiOperation(value = "Crear/Editar una Entidad", notes = "servicio para crear o editar entidades")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Entidad creada correctamente"),
        ApiResponse(code = 401, message = "Usuario No Autorizado"),
        ApiResponse(code = 403, message = "Solicitud prohibida por el servidor"),
        ApiResponse(code = 404, message = "Entidad no encontrada")])
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun save(@RequestBody entity: @Valid T?, result: BindingResult): ResponseEntity<*>? {
        return if (result.hasErrors()) {
            validar(result)
        } else ResponseEntity.status(HttpStatus.OK).body<Any>(serviceAPI!!.save(entity!!))
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "Eliminar una Entidad", notes = "servicio para eliminar entidades")
    @ApiResponses(value = [ApiResponse(code = 200, message = "Entidad eliminada correctamente"), ApiResponse(code = 401, message = "Usuario No Autorizado"), ApiResponse(code = 403, message = "Solicitud prohibida por el servidor"), ApiResponse(code = 404, message = "Entidad no encontrada")])
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun delete(@PathVariable id: ID): ResponseEntity<*>? {
        val entity: T = serviceAPI!!.getOne(id)
        if (entity != null) {
            serviceAPI!!.delete(id)
        } else {
            return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity<Any>(entity, HttpStatus.OK)
    }
}