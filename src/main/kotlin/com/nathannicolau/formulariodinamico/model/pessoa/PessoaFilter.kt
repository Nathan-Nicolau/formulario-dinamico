package com.nathannicolau.formulariodinamico.model.pessoa

import com.nathannicolau.formulariodinamico.model.Filter
import java.io.Serializable

open class PessoaFilter(
    override val id: String? = null,
    open val nome: String? = null,
    open val email: String? = null,
    override val dinamicos: Map<String, Any>? = null,
    override val pagina: Int? = null,
    override val tamanho: Int? = null
): Filter(id = id, dinamicos = dinamicos), Serializable
