package com.nathannicolau.formulariodinamico.model.produto

class ProdutoUpdateFilter(
    override val id: String? = null,
    override val nome: String? = null,
    override val descricao: String? = null,
    val valor: Double? = null,
    override val dinamicos: Map<String, Any>? = null
): ProdutoFilter(id, nome, descricao, dinamicos) {

    override fun validarAtualizacao() {
        require(id != null) { "O ID do produto é obrigatório para atualização" }
        require(nome != null || descricao != null || valor != null || dinamicos != null) {
            "Pelo menos um campo (nome, descrição, valor ou dinamicos) deve ser informado para atualização"
        }
    }

}