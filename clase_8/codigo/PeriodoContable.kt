package com.applimarket.practicas_uab.periodo

/**

 * Que es genuinamente unico en el dominio: EL PERIODO CONTABLE ABIERTO.
 *
 * Solo puede haber un periodo abierto a la vez, y todo movimiento se registra
 * contra ese periodo.
 * Si hubiera dos abiertos, un mismo movimiento podria caer en cualquiera de los
 * dos y la valorizacion del inventario dejaria de ser reproducible.
 */

data class Movimiento(
    val producto: String,
    val cantidad: Int
)

object PeriodoContable {

    var mes: String = "2026-09"
        private set

    var abierto: Boolean = true
        private set

    private val movimientos = mutableListOf<Movimiento>()

    /** Todo movimiento queda registrado contra el unico periodo abierto. */
    fun registrar(movimiento: Movimiento) {
        check(abierto) { "El periodo $mes esta cerrado: no acepta movimientos" }
        movimientos.add(movimiento)
    }

    fun cantidadMovimientos(): Int = movimientos.size

    fun cerrar() {
        check(abierto) { "El periodo $mes ya estaba cerrado" }
        abierto = false
    }

    fun abrir(nuevoMes: String) {
        check(!abierto) { "No se puede abrir $nuevoMes: el periodo $mes sigue abierto" }
        mes = nuevoMes
        abierto = true
        movimientos.clear()
    }

    /**
     * ATENCION: este metodo no deberia existir en produccion.
     *
     * Existe unicamente porque el Singleton guarda estado global y compartido,
     * asi que las pruebas se contaminan entre si. Tener que agregar un
     * "reiniciar" solo para poder testear ES la senal de que el patron empieza
     * a estorbar: el estado mutable global es la puerta al antipatron.
     */
    internal fun reiniciarParaPruebas(mes: String = "2026-09") {
        this.mes = mes
        this.abierto = true
        this.movimientos.clear()
    }
}
