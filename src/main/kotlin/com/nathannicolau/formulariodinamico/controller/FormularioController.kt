package com.nathannicolau.formulariodinamico.controller

import com.nathannicolau.formulariodinamico.model.Pessoa
import com.nathannicolau.formulariodinamico.model.PessoaFilter
import com.nathannicolau.formulariodinamico.model.PessoaResponse
import com.nathannicolau.formulariodinamico.service.PessoaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.SQLException

@RestController(value = "formulario")
@RequestMapping("/api/formulario")
class FormularioController(
    val pessoaService: PessoaService
) {

    @PostMapping("cadastrar")
    fun cadastrarPessoa(@RequestBody pessoa: Pessoa): PessoaResponse {
        return try {
            PessoaResponse.sucesso("Pessoa cadastrada com sucesso: ${pessoaService.cadastrarPessoa(pessoa).id}")
        } catch (e: Exception) {
            PessoaResponse.erro("Erro ao cadastrar pessoa: ${e.message}")
        }
    }

    @PostMapping("recuperar")
    fun recuperarPessoa(@RequestBody pessoaFilter: PessoaFilter): PessoaResponse {
        return try {
            val pessoas = pessoaService.recuperarPessoa(pessoaFilter)
            val mensagem = if (pessoas.isEmpty()) {
                "Nenhuma pessoa encontrada com os filtros informados"
            } else {
                "${pessoas.size} Pessoa(s) encontrada(s)"
            }
            PessoaResponse.data(mensagem = mensagem, data = pessoas)
        }  catch (e: Exception) {
            PessoaResponse.erro("Erro ao recuperar pessoa: ${e.message}")
        }
    }

}