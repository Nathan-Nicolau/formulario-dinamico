package com.nathannicolau.formulariodinamico.model

import java.io.Serializable

data class Pagina<T>(
    val numeroPagina: Int = 0,
    val totalPaginas: Int = 0,
    val totalRegistros: Int = 0,
    val data: List<T> = emptyList(),
): Serializable {

    companion object {

        fun <T> pagina(
            numeroPagina: Int,
            totalPaginas: Int,
            totalRegistros: Int,
            data: List<T>
        ): Pagina<T> {
            return Pagina(
                numeroPagina = numeroPagina,
                totalPaginas = totalPaginas,
                totalRegistros = totalRegistros,
                data = data
            )
        }

        // Constrói a Pagina calculando o totalPaginas a partir do tamanho da página
        fun <T> comCalculo(
            numeroPagina: Int,
            tamanhoPagina: Int,
            totalRegistros: Int,
            data: List<T>
        ): Pagina<T> {
            val totalPaginas = calcularTotalPaginas(totalRegistros, tamanhoPagina)
            return Pagina(
                numeroPagina = numeroPagina,
                totalPaginas = totalPaginas,
                totalRegistros = totalRegistros,
                data = data
            )
        }

        fun calcularTotalPaginas(totalRegistros: Int, tamanhoPagina: Int): Int {
            if (tamanhoPagina <= 0 || totalRegistros <= 0) return 0
            return kotlin.math.ceil(totalRegistros.toDouble() / tamanhoPagina).toInt()
        }

        fun <T> simples(data: List<T>): Pagina<T> {
            return Pagina(
                numeroPagina = 1,
                totalPaginas = if (data.isEmpty()) 0 else 1,
                totalRegistros = data.size,
                data = data
            )
        }

    }

}