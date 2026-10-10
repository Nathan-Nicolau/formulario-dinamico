package com.nathannicolau.formulariodinamico.repository

import com.nathannicolau.formulariodinamico.model.Pessoa
import com.nathannicolau.formulariodinamico.model.PessoaFilter

interface PessoaRepositoryCustom {

    fun buscarDinamicamente(pessoaFilter: PessoaFilter): List<Pessoa>

}