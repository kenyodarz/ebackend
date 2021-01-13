package com.cdmservicios.ebackend.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "proyecto")
class Proyecto {

    @Id
    var idProyecto: String? = null

    @Column
    var nombre: String? = null
}