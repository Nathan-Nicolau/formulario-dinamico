package com.nathannicolau.formulariodinamico.controller.pessoa

import com.nathannicolau.formulariodinamico.model.pessoa.Pessoa
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaFilter
import com.nathannicolau.formulariodinamico.model.FormularioResponse
import com.nathannicolau.formulariodinamico.service.pessoa.PessoaService
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaUpdateFilter
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController(value = "formulario-pessoas")
@RequestMapping("/api/pessoas")
class PessoaController(
    val pessoaService: PessoaService
) {

    @PostMapping("cadastrar")
    fun cadastrarPessoa(@RequestBody pessoa: Pessoa): FormularioResponse<Pessoa> {
        return try {
            FormularioResponse.sucesso("Pessoa cadastrada com sucesso: ${pessoaService.cadastrarPessoa(pessoa).id}")
        } catch (e: Exception) {
            FormularioResponse.erro("Erro ao cadastrar pessoa: ${e.message}")
        }
    }

    @PostMapping("cadastrarEmLote")
    fun cadastrarPessoasEmLote(@RequestBody pessoas: List<Pessoa>): FormularioResponse<Pessoa> {
        return try {
            val cadastradas = pessoaService.cadastrarPessoas(pessoas)
            FormularioResponse.sucesso("Pessoas cadastradas com sucesso: ${cadastradas.map { "Nome: " + it.nome + "ID: " + it.id }}")
        } catch (e: Exception) {
            FormularioResponse.erro("Erro ao cadastrar pessoa: ${e.message}")
        }
    }

    /**
     * Recuperação dos registros de pessoas com paginação aplicada dentro da classe PessoaFilter
     * */
    @PostMapping("recuperar")
    fun recuperarPessoa(@RequestBody pessoaFilter: PessoaFilter): FormularioResponse<Pessoa> {
        return try {
            val pagina = pessoaService.recuperarPessoa(pessoaFilter)
            val mensagem = if (pagina.data.isEmpty()) {
                "Nenhuma pessoa encontrada com os filtros informados"
            } else {
                "${pagina.data.size} Pessoa(s) encontrada(s)"
            }
            FormularioResponse.data(mensagem = mensagem, pagina = pagina)
        }  catch (e: Exception) {
            FormularioResponse.erro("Erro ao recuperar as pessoas: ${e.message}")
        }
    }

    @PutMapping("atualizar")
    fun atualizarPessoa(@RequestBody pessoaUpdateFilter: PessoaUpdateFilter): FormularioResponse<Pessoa> {
        return try {
            val pessoaAtualizada = pessoaService.atualizarPessoa(pessoaUpdateFilter)
            FormularioResponse.data(mensagem = "Pessoa atualizada com sucesso", data = listOf(pessoaAtualizada))
        } catch (e: Exception) {
            FormularioResponse.erro("Erro ao atualizar pessoa: ${e.message}")
        }
    }

}