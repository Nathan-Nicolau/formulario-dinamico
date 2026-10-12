package com.nathannicolau.formulariodinamico.repository.pessoa

import com.nathannicolau.formulariodinamico.model.pessoa.Pessoa
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaFilter
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaUpdateFilter

interface PessoaRepositoryCustom {

    fun buscarDinamicamente(pessoaFilter: PessoaFilter): List<Pessoa>

    fun atualizarPessoa(pessoaUpdateFilter: PessoaUpdateFilter): Pessoa

}