package com.nathannicolau.formulariodinamico.repository.pessoa

import com.nathannicolau.formulariodinamico.model.pessoa.Pessoa
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaFilter
import com.nathannicolau.formulariodinamico.model.pessoa.PessoaUpdateFilter
import com.nathannicolau.formulariodinamico.repository.Utils
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class PessoaRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate
) : PessoaRepositoryCustom {

    override fun buscarDinamicamente(pessoaFilter: PessoaFilter): List<Pessoa> {
        var query = Query()

        pessoaFilter.id?.takeIf { it.isNotBlank() }?.let { id ->
            query.addCriteria(Criteria.where("_id").`is`(id))
        }

        pessoaFilter.nome?.takeIf { it.isNotBlank() }?.let { nome ->
            query.addCriteria(Criteria.where("nome").regex(nome, "i"))
            // Esse regex aqui é aplicado com um like de valor, e o "i" na parte de options, determinada
            // para lidar como case-insensitive
        }

        pessoaFilter.email?.takeIf { it.isNotBlank() }?.let { email ->
            query.addCriteria(Criteria.where("email").regex(email, "i"))
        }

        pessoaFilter.dinamicos?.forEach { (chave, valor) ->
            if (valor is String && valor.isNotBlank()) {
                query.addCriteria(Criteria.where("dinamicos.$chave").regex(valor, "i"))
            } else if (valor !is String) {
                query.addCriteria(Criteria.where("dinamicos.$chave").`is`(valor))
            }
        }

        query = Utils.agruparConsultasCamposDinamicos(query, pessoaFilter.dinamicos)

        query.with(pessoaFilter.montarPageRequest())

        // O MongoTemplate executa a query construída contra a coleção da classe Pessoa
        return mongoTemplate.find(query, Pessoa::class.java)
    }

    //Atualização de registro de pessoa com busca inicial por id
    override fun atualizarPessoa(pessoaUpdateFilter: PessoaUpdateFilter): Pessoa {
        val query = Query(Criteria.where("_id").`is`(pessoaUpdateFilter.id))
        val update = Update()

        pessoaUpdateFilter.nome?.takeIf { it.isNotBlank() }?.let {
            update.set("nome", it)
        }

        pessoaUpdateFilter.email?.takeIf { it.isNotBlank() }?.let {
            update.set("email", it)
        }

        pessoaUpdateFilter.dinamicos?.forEach { (chave, valor) ->
            update.set("dinamicos.$chave", valor)
        }

        val options = FindAndModifyOptions.options().returnNew(true)

        return mongoTemplate.findAndModify(query, update, options, Pessoa::class.java)
            ?: throw NoSuchElementException("Pessoa com o ID: ${pessoaUpdateFilter.id} não encontrada")
    }

}