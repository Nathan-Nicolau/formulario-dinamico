package com.nathannicolau.formulariodinamico.model

import java.io.Serializable
import java.util.Date

data class PessoaResponse(
    val codigo: String,
    val mensagem: String,
    val momento: String = Date().toString(),
    val data: List<Pessoa>
): Serializable {

    companion object {

        private fun response(codigo: String, mensagem: String, data: List<Pessoa> = emptyList()): PessoaResponse {
            return PessoaResponse(codigo, mensagem, data = data)
        }

        fun erro(mensagem: String): PessoaResponse {
            return response(codigo = "500", mensagem = mensagem)
        }

        fun sucesso(mensagem: String): PessoaResponse {
            return response(codigo = "200", mensagem = mensagem)
        }

        fun data(mensagem: String, data: List<Pessoa>): PessoaResponse {
            return response(codigo = "200", mensagem = mensagem, data = data)
        }

    }

}
