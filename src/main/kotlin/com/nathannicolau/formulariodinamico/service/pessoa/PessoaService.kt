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
        require(!pessoa.nome.isNullOrBlank()) { "O nome da pessoa é obrigatório" }
        require(!pessoa.email.isNullOrBlank()) { "O email da pessoa é obrigatório" }

        val emailExistente = pessoaRepository.findByEmail(pessoa.email)
        if (emailExistente != null) {
            throw IllegalArgumentException("Já existe uma pessoa cadastrada com o email: ${pessoa.email}")
        }

        return pessoaRepository.save(pessoa)
    }

    fun recuperarPessoa(pessoaFilter: PessoaFilter): Pagina<Pessoa> {
        pessoaFilter.pagina?.let {
            require(it >= 0) { "O número da página não pode ser negativo" }
        }
        pessoaFilter.tamanho?.let {
            require(it > 0) { "O tamanho da página deve ser maior que zero" }
        }

        val pessoas = pessoaRepository.buscarDinamicamente(pessoaFilter)

        return Pagina.comCalculo(
            numeroPagina = pessoaFilter.pagina ?: 1,
            tamanhoPagina = pessoaFilter.tamanho ?: pessoas.size,
            totalRegistros = pessoas.size,
            data = pessoas
        )
    }

    fun atualizarPessoa(pessoaUpdateFilter: PessoaUpdateFilter): Pessoa {
        require(pessoaUpdateFilter.id.isNotBlank()) { "O ID da pessoa é obrigatório para atualização" }

        val temCamposParaAtualizar = !pessoaUpdateFilter.nome.isNullOrBlank() ||
                !pessoaUpdateFilter.email.isNullOrBlank() ||
                !pessoaUpdateFilter.dinamicos.isNullOrEmpty()

        require(temCamposParaAtualizar) {
            "Pelo menos um campo (nome, email ou dinamicos) deve ser informado para atualização"
        }

        pessoaUpdateFilter.email?.takeIf { it.isNotBlank() }?.let { novoEmail ->
            val pessoaComMesmoEmail = pessoaRepository.findByEmail(novoEmail)
            if (pessoaComMesmoEmail != null && pessoaComMesmoEmail.id != pessoaUpdateFilter.id) {
                throw IllegalArgumentException("O email '$novoEmail' já está em uso por outro registro")
            }
        }

        return pessoaRepository.atualizarPessoa(pessoaUpdateFilter)
    }

}