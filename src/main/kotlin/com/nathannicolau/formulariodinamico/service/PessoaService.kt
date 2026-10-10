package com.nathannicolau.formulariodinamico.service

import com.nathannicolau.formulariodinamico.model.Pessoa
import com.nathannicolau.formulariodinamico.model.PessoaFilter
import com.nathannicolau.formulariodinamico.repository.PessoaRepository
import com.nathannicolau.formulariodinamico.repository.PessoaRepositoryCustom
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PessoaService(
    val pessoaRepository: PessoaRepository
) {

    fun cadastrarPessoa(pessoa: Pessoa): Pessoa {
        return pessoaRepository.save(pessoa)
    }

    fun recuperarPessoa(pessoaFilter: PessoaFilter): List<Pessoa> {
        return pessoaRepository.buscarDinamicamente(pessoaFilter)
    }

}