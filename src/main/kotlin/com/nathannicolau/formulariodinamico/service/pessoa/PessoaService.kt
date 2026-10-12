package com.nathannicolau.formulariodinamico.service.pessoa

import com.nathannicolau.formulariodinamico.model.Pagina
import com.nathannicolau.formulariodinamico.model.pessoa.Pessoa
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaFilter
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaUpdateFilter
import com.nathannicolau.formulariodinamico.repository.pessoa.PessoaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PessoaService(
    val pessoaRepository: PessoaRepository
) {

    fun cadastrarPessoa(pessoa: Pessoa): Pessoa {
        pessoa.validarCadastro()

        val emailExistente = pessoaRepository.findByEmail(pessoa.email!!)
        if (emailExistente != null) {
            throw IllegalArgumentException("Já existe uma pessoa cadastrada com o email: ${pessoa.email}")
        }

        return pessoaRepository.save(pessoa)
    }

    fun cadastrarPessoas(pessoas: List<Pessoa>): List<Pessoa> {
        pessoas.forEach { pessoa -> pessoa.validarCadastro()}
        return pessoaRepository.saveAll(pessoas)
    }

    fun recuperarPessoa(pessoaFilter: PessoaFilter): Pagina<Pessoa> {
        pessoaFilter.validarPaginacao()

        val pessoas = pessoaRepository.buscarDinamicamente(pessoaFilter)

        return Pagina.comCalculo(
            numeroPagina = pessoaFilter.pagina ?: 1,
            tamanhoPagina = pessoaFilter.tamanho ?: pessoas.size,
            totalRegistros = pessoas.size,
            data = pessoas
        )
    }

    fun atualizarPessoa(pessoaUpdateFilter: PessoaUpdateFilter): Pessoa {
        pessoaUpdateFilter.validarAtualizacao()

        pessoaUpdateFilter.email?.takeIf { it.isNotBlank() }?.let { novoEmail ->
            val pessoaComMesmoEmail = pessoaRepository.findByEmail(novoEmail)
            if (pessoaComMesmoEmail != null && pessoaComMesmoEmail.id != pessoaUpdateFilter.id) {
                throw IllegalArgumentException("O email '$novoEmail' já está em uso por outro registro")
            }
        }

        return pessoaRepository.atualizarPessoa(pessoaUpdateFilter)
    }

}