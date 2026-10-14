package com.nathannicolau.formulariodinamico.controller.produto

import com.nathannicolau.formulariodinamico.model.FormularioResponse
import com.nathannicolau.formulariodinamico.model.produto.Produto
import com.nathannicolau.formulariodinamico.model.produto.ProdutoFilter
import com.nathannicolau.formulariodinamico.model.produto.ProdutoUpdateFilter
import com.nathannicolau.formulariodinamico.service.pessoa.ProdutoService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @PostMapping("cadastrarEmLote")
    fun cadastrarProdutosEmLote(@RequestBody produtos: List<Produto>): FormularioResponse<Produto> {
        return try {
            FormularioResponse.data(mensagem = "Produtos cadastrados com sucesso", data = produtoService.cadastrarProdutos(produtos))
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro ao cadastrar os produtos: ${e.message}")
        }
    }

    @PostMapping("recuperar")
    fun recuperarProdutos(produtoFilter: ProdutoFilter): FormularioResponse<Produto> {
        return try {
            val paginaProdutos = produtoService.recuperarProdutos(produtoFilter)
            FormularioResponse.data(mensagem = "Produtos recuperados com sucesso", pagina = paginaProdutos)
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro na recuperação dos produtos: ${e.message}")
        }
    }

    @PutMapping("atualizar")
    fun atualizarProduto(@RequestBody produtoUpdateFilter: ProdutoUpdateFilter): FormularioResponse<Produto> {
        return try {
            FormularioResponse.data(mensagem = "Produto atualizado com sucesso", data = listOf(produtoService.atualizarProduto(produtoUpdateFilter)))
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro ao atualizar o produtos: ${e.message}")
        }
    }

}