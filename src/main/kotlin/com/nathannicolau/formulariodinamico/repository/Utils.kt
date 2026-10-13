package com.nathannicolau.formulariodinamico.repository

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

object Utils {

    fun agruparConsultasCamposDinamicos(query: Query, camposDinamicos: Map<String, Any>?): Query {
        return if (camposDinamicos != null) {
            camposDinamicos.forEach { (chave, valor) ->
                if (valor is String && valor.isNotBlank()) {
                    query.addCriteria(Criteria.where("dinamicos.$chave").regex(valor, "i"))
                } else if (valor !is String) {
                    query.addCriteria(Criteria.where("dinamicos.$chave").`is`(valor))
                }
            }
            query
        } else query
    }


}