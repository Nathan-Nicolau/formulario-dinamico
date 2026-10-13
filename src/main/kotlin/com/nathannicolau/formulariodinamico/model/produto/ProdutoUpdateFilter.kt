package com.nathannicolau.formulariodinamico.model.produto

class ProdutoUpdateFilter(
    override val id: String,
    override val nome: String? = null,
    override val descricao: String? = null,
    val valor: Double? = null,
    override val dinamicos: Map<String, Any>? = null
): ProdutoFilter(id, nome, descricao, dinamicos) {
}