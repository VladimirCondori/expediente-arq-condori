// Solucion: Vladimir Condori

interface TarifaFranja {
    fun calcular(tarifaHora: Double, horas: Int): Double
}

class TarifaManana : TarifaFranja {
    override fun calcular(tarifaHora: Double, horas: Int): Double {
        return tarifaHora * horas
    }
}

class TarifaNoche : TarifaFranja {
    override fun calcular(tarifaHora: Double, horas: Int): Double {
        val precioNoche = tarifaHora * 1.20
        return precioNoche * horas
    }
}

class TarifaFinDeSemana : TarifaFranja {
    override fun calcular(tarifaHora: Double, horas: Int): Double {
        val precioDescuento = tarifaHora * 0.70
        if (horas <= 3) {
            return precioDescuento * horas
        }
        val horasExtra = horas - 3
        return precioDescuento * 3 + tarifaHora * horasExtra
    }
}

class CalculadoraTarifa(val tarifaHora: Double) {
    fun calcularTotal(franja: TarifaFranja, horas: Int): Double {
        return franja.calcular(tarifaHora, horas)
    }
}

fun main() {
    val calculadora = CalculadoraTarifa(15.0)

    val cobroManana = calculadora.calcularTotal(TarifaManana(), 2)
    println("Cobro socio mañana 2 horas: Bs $cobroManana")

    val cobroNoche = calculadora.calcularTotal(TarifaNoche(), 2)
    println("Cobro socio noche 2 horas: Bs $cobroNoche")

    val cotizacion = calculadora.calcularTotal(TarifaFinDeSemana(), 5)
    println("Cotizacion fin de semana 5 horas: Bs $cotizacion")
}
