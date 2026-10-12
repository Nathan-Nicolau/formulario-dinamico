package com.nathannicolau.formulariodinamico.model.pessoa

/**
 * Essa data classe serve somente como ponte de atualização do valor Pessoa,
 * obrigatoriamente exigindo o valor do id
 * */

data class PessoaUpdateFilter(
    override val id: String,
    override val nome: String? = null,
    override val email: String? = null,
    override val dinamicos: Map<String, Any>? = null
): PessoaFilter(id, nome, email, dinamicos)
