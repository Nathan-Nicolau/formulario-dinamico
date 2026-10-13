package com.nathannicolau.formulariodinamico.controller.produto

import com.nathannicolau.formulariodinamico.model.FormularioResponse
import com.nathannicolau.formulariodinamico.model.produto.Produto
import com.nathannicolau.formulariodinamico.service.pessoa.ProdutoService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController(value = "formulario-produtos")
@RequestMapping("/api/produtos")
class ProdutoController(
    val produtoService: ProdutoService
) {

    @PostMapping("cadastrar")
    fun cadastrarProduto(@RequestBody produto: Produto): FormularioResponse<Produto> {
        return try {
            FormularioResponse.sucesso(mensagem = "Produto cadastrado com sucesso: ${produtoService.cadastrarProduto(produto).id}")
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro ao cadastrar o produto: ${e.message}")
        }
    }

}