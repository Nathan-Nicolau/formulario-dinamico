package com.nathannicolau.formulariodinamico.model

import java.io.Serializable

data class PessoaFilter(
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val dinamicos: Map<String, String>? = null
): Serializable
