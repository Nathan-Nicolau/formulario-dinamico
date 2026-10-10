package com.nathannicolau.formulariodinamico.repository

import com.nathannicolau.formulariodinamico.model.Pessoa
import com.nathannicolau.formulariodinamico.model.PessoaFilter
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class PessoaRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate
) : PessoaRepositoryCustom {

    override fun buscarDinamicamente(pessoaFilter: PessoaFilter): List<Pessoa> {
        val query = Query()

        pessoaFilter.id?.takeIf { it.isNotBlank() }?.let { id ->
            query.addCriteria(Criteria.where("_id").`is`(id))
        }

        pessoaFilter.nome?.takeIf { it.isNotBlank() }?.let { nome ->
            query.addCriteria(Criteria.where("nome").regex(nome, "i"))
        }

        pessoaFilter.email?.takeIf { it.isNotBlank() }?.let { email ->
            query.addCriteria(Criteria.where("email").`is`(email))
        }

        pessoaFilter.dinamicos?.filter { it.value.isNotBlank() }?.forEach { (chave, valor) ->
            query.addCriteria(Criteria.where("dinamicos.$chave").`is`(valor))
        }

        // O MongoTemplate executa a query construída contra a coleção da classe Pessoa
        return mongoTemplate.find(query, Pessoa::class.java)
    }
}