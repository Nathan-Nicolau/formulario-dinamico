package com.nathannicolau.formulariodinamico.repository.produto

import com.nathannicolau.formulariodinamico.model.produto.Produto
import com.nathannicolau.formulariodinamico.model.produto.ProdutoFilter
import com.nathannicolau.formulariodinamico.model.produto.ProdutoUpdateFilter
import com.nathannicolau.formulariodinamico.repository.Utils
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class ProdutoRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate
) : ProdutoRepositoryCustom {

    override fun buscarDinamicamente(produtoFilter: ProdutoFilter): List<Produto> {
        var query = Query()

        produtoFilter.id?.takeIf { it.isNotBlank() }?.let {
            query.addCriteria(Criteria.where("_id").`is`(it))
        }

        produtoFilter.nome?.takeIf { it.isNotBlank() }?.let {
            query.addCriteria(Criteria.where("nome").regex(it, "i"))
        }

        produtoFilter.descricao?.takeIf { it.isNotBlank() }?.let {
            query.addCriteria(Criteria.where("descricao").regex(it, "i"))
        }

        produtoFilter.valorInicial?.takeIf { !it.isNaN() }?.let {
            query.addCriteria(Criteria.where("valor").gte(it))
        }

        produtoFilter.valorFinal?.takeIf { !it.isNaN() }?.let {
            query.addCriteria(Criteria.where("valor").lte(it))
        }

        query = Utils.agruparConsultasCamposDinamicos(query, produtoFilter.dinamicos)

        query.with(produtoFilter.montarPageRequest())

        return mongoTemplate.find(query, Produto::class.java)

    }

    override fun atualizarProduto(produtoUpdateFilter: ProdutoUpdateFilter): Produto {
        val query = Query(Criteria.where("id").`is`(produtoUpdateFilter.id))
        val update = Update()

        produtoUpdateFilter.nome?.takeIf { it.isNotBlank() }?.let {
            update.set("nome", it)
        }

        produtoUpdateFilter.descricao?.takeIf { it.isNotBlank() }?.let {
            update.set("descricao", it)
        }

        produtoUpdateFilter.valor?.takeIf { !it.isNaN() }?.let {
            update.set("valor", it)
        }

        produtoUpdateFilter.dinamicos?.forEach { (chave, valor) ->
            update.set("dinamicos.$chave", valor)
        }

        val options = FindAndModifyOptions.options().returnNew(true)

        return mongoTemplate.findAndModify(query, update, options, Produto::class.java)
            ?: throw NoSuchElementException("Produto com o ID: ${produtoUpdateFilter.id} não encontrado")

    }

}