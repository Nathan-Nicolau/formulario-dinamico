package com.nathannicolau.formulariodinamico.model.produto

import com.nathannicolau.formulariodinamico.model.Filter
import java.io.Serializable

open class ProdutoFilter(
    override val id: String? = null,
    open val nome: String? = null,
    open val descricao: String? = null,
    val valorInicial: Double? = null,
    val valorFinal: Double? = null,
    override val dinamicos: Map<String, Any>? = null
): Filter(id, dinamicos), Serializable {

    constructor(id: String, nome: String?, descricao: String?, dinamicos: Map<String, Any>? = null): this(
        id = id,
        nome = nome,
        descricao = descricao,
        valorInicial = null,
        valorFinal = null,
        dinamicos = dinamicos
    )

}
