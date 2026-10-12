package com.nathannicolau.formulariodinamico.model

import java.io.Serializable
import java.util.Date

data class FormularioResponse<T>(
    val codigo: String,
    val mensagem: String,
    val momento: String = Date().toString(),
    val data: Pagina<T>? = null,
): Serializable {

    companion object {

        private fun <T> response(codigo: String, mensagem: String, data: Pagina<T> = Pagina.simples(emptyList())): FormularioResponse<T> {
            return FormularioResponse(codigo, mensagem, data = data)
        }

        fun <T> erro(mensagem: String): FormularioResponse<T> {
            return response(codigo = "500", mensagem = mensagem)
        }

        fun <T> sucesso(mensagem: String): FormularioResponse<T> {
            return response(codigo = "200", mensagem = mensagem)
        }

        fun <T> data(
            mensagem: String,
            data: List<T>,
            numeroPagina: Int = 1,
            tamanhoPagina: Int = data.size,
            totalRegistros: Int = data.size
        ): FormularioResponse<T> {
            return response(
                codigo = "200",
                mensagem = mensagem,
                data = Pagina.comCalculo(
                    numeroPagina = numeroPagina,
                    tamanhoPagina = tamanhoPagina,
                    totalRegistros = totalRegistros,
                    data = data
                )
            )
        }

        fun <T> data(
            mensagem: String,
            pagina: Pagina<T>
        ): FormularioResponse<T> {
            return response(codigo = "200", mensagem = mensagem, data = pagina)
        }

    }

}
