package com.nathannicolau.formulariodinamico.repository.produto

import com.nathannicolau.formulariodinamico.model.produto.Produto
import com.nathannicolau.formulariodinamico.model.produto.ProdutoFilter
import com.nathannicolau.formulariodinamico.model.produto.ProdutoUpdateFilter

interface ProdutoRepositoryCustom {

    fun buscarDinamicamente(produtoFilter: ProdutoFilter): List<Produto>

    fun atualizarProduto(produtoUpdateFilter: ProdutoUpdateFilter): Produto

}