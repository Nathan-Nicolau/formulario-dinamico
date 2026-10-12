package com.nathannicolau.formulariodinamico.model.pessoa

import java.io.Serializable

open class PessoaFilter(
    open val id: String? = null,
    open val nome: String? = null,
    open val email: String? = null,
    open val dinamicos: Map<String, Any>? = null,
    val pagina: Int? = null,
    val tamanho: Int? = null,
): Serializable {

    fun possuiPaginacao() = pagina != null && tamanho != null

}
