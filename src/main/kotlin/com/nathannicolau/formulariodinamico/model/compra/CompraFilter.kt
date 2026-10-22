package com.nathannicolau.formulariodinamico.model.compra

import com.nathannicolau.formulariodinamico.model.Filter
import com.nathannicolau.formulariodinamico.model.pessoa.Pessoa
import com.nathannicolau.formulariodinamico.model.produto.Produto
import java.io.Serializable

open class CompraFilter(
    override val id: String? = null,
    open val nome: String? = null,
    open val produtos: List<Produto>? = null,
    open val pessoa: Pessoa? = null,
    override val dinamicos: Map<String, Any>? = null,
    override val pagina: Int? = null,
    override val tamanho: Int? = null
): Filter(id = id, dinamicos = dinamicos, pagina = pagina, tamanho = tamanho), Serializable
