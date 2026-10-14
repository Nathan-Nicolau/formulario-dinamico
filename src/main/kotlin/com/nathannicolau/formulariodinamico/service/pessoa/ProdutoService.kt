package com.nathannicolau.formulariodinamico.service.pessoa

import com.nathannicolau.formulariodinamico.model.Pagina
import com.nathannicolau.formulariodinamico.model.produto.Produto
import com.nathannicolau.formulariodinamico.model.produto.ProdutoFilter
import com.nathannicolau.formulariodinamico.model.produto.ProdutoUpdateFilter
import com.nathannicolau.formulariodinamico.repository.produto.ProdutoReposity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProdutoService(
    val produtoReposity: ProdutoReposity
) {

    fun cadastrarProduto(produto: Produto): Produto {
        produto.validarCadastro()
        return produtoReposity.save(produto)
    }

    fun cadastrarProdutos(produtos: List<Produto>): List<Produto> {
        produtos.forEach { produto -> produto.validarCadastro() }
        return produtoReposity.saveAll(produtos)
    }

    fun recuperarProdutos(produtoFilter: ProdutoFilter): Pagina<Produto> {
        produtoFilter.validarPaginacao()
        val produtos = produtoReposity.buscarDinamicamente(produtoFilter)
        return Pagina.comCalculo(
            numeroPagina = produtoFilter.pagina ?: 1,
            tamanhoPagina = produtoFilter.tamanho ?: 10,
            totalRegistros = produtos.size,
            data = produtos
        )
    }

    fun atualizarProduto(produtoUpdateFilter: ProdutoUpdateFilter): Produto {
        produtoUpdateFilter.validarAtualizacao()
        return produtoReposity.atualizarProduto(produtoUpdateFilter)
    }

}