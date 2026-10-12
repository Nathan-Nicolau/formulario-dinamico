package com.nathannicolau.formulariodinamico.model.pessoa

/**
 * Esse data classe serve somente como ponte de atualização do valor Pessoa,
 * obrigatoriamente exigindo o valor do id
 * */

data class PessoaUpdateFilter(
    override val id: String,
    override val nome: String? = null,
    override val email: String? = null,
    override val dinamicos: Map<String, Any>? = null
): PessoaFilter(id, nome, email, dinamicos) {

    fun validarAtualizacao() {
        require(id.isNotBlank()) { "O ID da pessoa é obrigatório para atualização" }
        require(!nome.isNullOrBlank() || !email.isNullOrBlank() || !dinamicos.isNullOrEmpty()) {
            "Pelo menos um campo (nome, email ou dinamicos) deve ser informado para atualização"
        }
    }

}
