package com.nathannicolau.formulariodinamico.model.compra

import com.nathannicolau.formulariodinamico.model.pessoa.Pessoa
import com.nathannicolau.formulariodinamico.model.produto.Produto

data class CompraUpdateFilter(
    override val id: String,
    override val nome: String? = null,
    override val produtos: List<Produto>? = null,
    override val pessoa: Pessoa? = null,
    override val dinamicos: Map<String, Any>? = null
): CompraFilter(id = id, nome = nome, produtos = produtos, pessoa = pessoa, dinamicos = dinamicos) {

    override fun validarAtualizacao() {
        require(id.isNotBlank()) { "O ID da compra é obrigatório para atualização" }
        require(!nome.isNullOrBlank() || !produtos.isNullOrEmpty() || pessoa != null || !dinamicos.isNullOrEmpty()) {
            "Pelo menos um campo (nome, produtos, pessoa ou dinamicos) deve ser informado para atualização"
        }
    }

}
