package com.cdmservicios.ebackend.core

import java.time.LocalTime
import com.cdmservicios.ebackend.models.Registro





class CalculoHoras {
    /* Declaración de variables no modificar */
    var hora = 0
    var minuto = 0
    var segundo = 0
    var festivo = 0
    var horas = arrayOf(
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
        "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
    )
    var minutos = arrayOf("0", "30", "59")
    var recargosNocturnos: Double = 0.0
    var horasOrdinarias: Double = 0.0
    var horasExtrasOrdinarias: Double = 0.0
    var horasExtrasNocturnas: Double = 0.0
    var horasExtrasOrdinariasFestivas: Double = 0.0
    var horasExtrasNocturnasFestivas: Double = 0.0
    var salarioSinPrestaciones: Double = 0.0
    var salarioConPrestaciones: Double = 0.0
    var prestaciones = 1.22
    var formatoHora = "HH:mm:ss"

    var rn1: LocalTime = LocalTime.of(21, 0, segundo)
    var rn2: LocalTime = LocalTime.of(23, 59, segundo)
    var rn3: LocalTime = LocalTime.of(0, 0, segundo)
    var rn4: LocalTime = LocalTime.of(5, 59, segundo)


    fun calcularHoras(horaEntrada: LocalTime, horaSalida: LocalTime, festivo: Boolean) {
        var i = horaEntrada
        while (i.isBefore(horaSalida)) {
            if (festivo) {
                if (i == rn3 && horasExtrasNocturnasFestivas != 0.0 && horasExtrasOrdinariasFestivas != 0.0) {
                    break
                } else {
                    if ((i.isBefore(rn4) || i == rn4) && (i.isAfter(rn3) || i == rn3)) {
                        if (horaEntrada == rn4) {
                            horasExtrasOrdinariasFestivas += 0.5
                        } else {
                            horasExtrasNocturnasFestivas += 0.5
                        }
                    } else if (i.isBefore(rn2) && (i.isAfter(rn1) || i == rn1)) {
                        horasExtrasNocturnasFestivas += 0.5
                    } else {
                        horasExtrasOrdinariasFestivas += 0.5
                    }
                }
            } else {
                if (i == rn3 && this.recargosNocturnos != 0.0 && this.horasOrdinarias != 0.0) {
                    break
                } else if (this.recargosNocturnos + this.horasOrdinarias < 8) {
                    if ((i.isBefore(rn4) || i == rn4) && (i.isAfter(rn3) || i == rn3)) {
                        if (horaEntrada == rn4) {
                            this.horasOrdinarias += 0.5
                        } else {
                            this.recargosNocturnos += 0.5
                        }
                    } else if ((i.isBefore(rn2) || i == rn2) && (i.isAfter(rn1) || i == rn1)) {
                        this.recargosNocturnos += 0.5
                    } else {
                        this.horasOrdinarias += 0.5
                    }
                } else {
                    if (i.isBefore(rn4) && i.isAfter(rn3)) {
                        horasExtrasNocturnas += 0.5
                    } else if ((i.isBefore(rn2) || i == rn2) && (i.isAfter(rn1) || i == rn1)) {
                        horasExtrasNocturnas += 0.5
                    } else {
                        horasExtrasOrdinarias += 0.5
                    }
                }
            }
            i = i.plusMinutes(30)
        }
    }

    fun calcularHorasSegundoRegistro(
        horaEntrada: LocalTime,
        horaSalida: LocalTime,
        festivo: Boolean,
        registro: Registro
    ) {
        var i = horaEntrada
        while (i.isBefore(horaSalida)) {
            if (festivo) {
                if (i == rn3 && horasExtrasNocturnasFestivas != 0.0 && horasExtrasOrdinariasFestivas != 0.0) {
                    break
                } else {
                    if ((i.isBefore(rn4) || i == rn4) && (i.isAfter(rn3) || i == rn3)) {
                        if (horaEntrada == rn4) {
                            horasExtrasOrdinariasFestivas += 0.5
                        } else {
                            horasExtrasNocturnasFestivas += 0.5
                        }
                    } else if (i.isBefore(rn2) && (i.isAfter(rn1) || i == rn1)) {
                        horasExtrasNocturnasFestivas += 0.5
                    } else {
                        horasExtrasOrdinariasFestivas += 0.5
                    }
                }
            } else {
                if (i == rn3) {
                    break
                } else if ((this.recargosNocturnos
                            + this.horasOrdinarias
                            + registro.horaOrdinaria!!
                            + registro.recargoNocturno!!) < 8
                ) {
                    if ((i.isBefore(rn4) || i == rn4) && (i.isAfter(rn3) || i == rn3)) {
                        if (horaEntrada == rn4) {
                            this.horasOrdinarias += 0.5
                        } else {
                            this.recargosNocturnos += 0.5
                        }
                    } else if ((i.isBefore(rn2) || i == rn2) && (i.isAfter(rn1) || i == rn1)) {
                        this.recargosNocturnos += 0.5
                    } else {
                        this.horasOrdinarias += 0.5
                    }
                } else {
                    if (i.isBefore(rn4) && i.isAfter(rn3)) {
                        horasExtrasNocturnas += 0.5
                    } else if ((i.isBefore(rn2) || i == rn2) && (i.isAfter(rn1) || i == rn1)) {
                        horasExtrasNocturnas += 0.5
                    } else {
                        horasExtrasOrdinarias += 0.5
                    }
                }
            }
            i = i.plusMinutes(30)
        }
    }

    fun calcularSueldo(salario: Double) {
        //salarioMínimo = 877803.0;
        val salarioBaseHora: Double = salario / 240 //, salarioMínimo;
        //salarioSinPrestaciones += horasOrdinarias*salarioBaseHora;
        salarioSinPrestaciones += recargosNocturnos * salarioBaseHora * 0.35
        salarioSinPrestaciones += horasExtrasOrdinarias * salarioBaseHora * 1.25
        salarioSinPrestaciones += horasExtrasNocturnas * salarioBaseHora * 1.75
        salarioSinPrestaciones += horasExtrasOrdinariasFestivas * salarioBaseHora * 2.00
        salarioSinPrestaciones += horasExtrasNocturnasFestivas * salarioBaseHora * 2.50
        salarioConPrestaciones = salarioSinPrestaciones * prestaciones
    }

}