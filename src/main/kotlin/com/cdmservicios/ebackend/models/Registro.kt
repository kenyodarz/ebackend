package com.cdmservicios.ebackend.models

import java.time.LocalDate
import javax.persistence.*
import javax.persistence.JoinColumn

import java.time.LocalTime




@Entity
@Table(name = "registro")
class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null

    @Column
    var fecha: LocalDate?=null

    @Column
    var horaEntrada: LocalTime? = null

    @Column
    var horaSalida: LocalTime? = null

    @Column
    var horaOrdinaria: Double? = null

    @Column
    var recargoNocturno: Double? = null

    @Column
    var horaExtra: Double? = null

    @Column
    var horaExtraNocturna: Double? = null

    @Column
    var horaExtraFestiva: Double? = null

    @Column
    var horaExtraFestivaNocturna: Double? = null

    @OneToOne
    @JoinColumn(name = "cedula")
    var persona: Persona? = null

    @OneToOne
    @JoinColumn(name = "id_proyecto")
    var proyecto: Proyecto? = null

    @Column
    var festivo = false

    @Column
    var salarioSinPrestaciones: Double? = null

    @Column
    var salarioConPrestaciones: Double? = null

    @Column
    var actividad: String? = null

    @Column
    var users: String? = null

}