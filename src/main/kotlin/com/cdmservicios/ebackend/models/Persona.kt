package com.cdmservicios.ebackend.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "persona")
class Persona {

    @Id
    var cedula: String? = null

    @Column
    var nombre: String? = null

    @Column
    var salario: Double? = null
}