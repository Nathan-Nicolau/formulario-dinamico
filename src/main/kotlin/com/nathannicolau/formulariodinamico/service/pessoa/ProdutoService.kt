package com.nathannicolau.formulariodinamico.service.pessoa

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
        return produtoReposity.save(produto)
    }

    fun buscarProdutosDinamicamente(produtoFilter: ProdutoFilter): List<Produto> {
        return produtoReposity.buscarDinamicamente(produtoFilter)
    }

    fun atualizarProduto(produtoUpdateFilter: ProdutoUpdateFilter): Produto {
        return produtoReposity.atualizarProduto(produtoUpdateFilter)
    }

}