package com.nathannicolau.formulariodinamico.repository.compra

import com.nathannicolau.formulariodinamico.model.compra.Compra
import com.nathannicolau.formulariodinamico.model.compra.CompraFilter
import com.nathannicolau.formulariodinamico.model.compra.CompraUpdateFilter
import com.nathannicolau.formulariodinamico.repository.Utils
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class CompraRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate
) : CompraRepositoryCustom {

    override fun buscarDinamicamente(compraFilter: CompraFilter): List<Compra> {
        var query = Query()

        compraFilter.id?.takeIf { it.isNotBlank() }?.let {
            query.addCriteria(Criteria.where("_id").`is`(it))
        }

        compraFilter.nome?.takeIf { it.isNotBlank() }?.let {
            query.addCriteria(Criteria.where("nome").regex(it, "i"))
        }

        compraFilter.pessoa?.let { pessoa ->
            pessoa.id?.takeIf { it.isNotBlank() }?.let {
                query.addCriteria(Criteria.where("pessoa.id").`is`(it))
            }
            pessoa.nome?.takeIf { it.isNotBlank() }?.let {
                query.addCriteria(Criteria.where("pessoa.nome").regex(it, "i"))
            }
            pessoa.email?.takeIf { it.isNotBlank() }?.let {
                query.addCriteria(Criteria.where("pessoa.email").regex(it, "i"))
            }
        }

        compraFilter.produtos?.takeIf { it.isNotEmpty() }?.forEach { produto ->
            val elemCriteria = Criteria()
            var hasCriteria = false

            produto.id?.takeIf { it.isNotBlank() }?.let {
                elemCriteria.and("id").`is`(it)
                hasCriteria = true
            }
            produto.nome?.takeIf { it.isNotBlank() }?.let {
                elemCriteria.and("nome").regex(it, "i")
                hasCriteria = true
            }
            produto.descricao?.takeIf { it.isNotBlank() }?.let {
                elemCriteria.and("descricao").regex(it, "i")
                hasCriteria = true
            }
            produto.valor?.let {
                elemCriteria.and("valor").`is`(it)
                hasCriteria = true
            }

            if (hasCriteria) {
                query.addCriteria(Criteria.where("produtos").elemMatch(elemCriteria))
            }
        }

        query = Utils.agruparConsultasCamposDinamicos(query, compraFilter.dinamicos)

        query.with(compraFilter.montarPageRequest())

        return mongoTemplate.find(query, Compra::class.java)
    }

    override fun atualizarCompra(compraUpdateFilter: CompraUpdateFilter): Compra {
        val query = Query(Criteria.where("_id").`is`(compraUpdateFilter.id))
        val update = Update()

        compraUpdateFilter.nome?.takeIf { it.isNotBlank() }?.let {
            update.set("nome", it)
        }

        compraUpdateFilter.produtos?.takeIf { it.isNotEmpty() }?.let {
            update.set("produtos", it)
        }

        compraUpdateFilter.pessoa?.let {
            update.set("pessoa", it)
        }

        compraUpdateFilter.dinamicos?.forEach { (chave, valor) ->
            update.set("dinamicos.$chave", valor)
        }

        val options = FindAndModifyOptions.options().returnNew(true)

        return mongoTemplate.findAndModify(query, update, options, Compra::class.java)
            ?: throw NoSuchElementException("Compra com o ID: ${compraUpdateFilter.id} não encontrada")
    }

}
