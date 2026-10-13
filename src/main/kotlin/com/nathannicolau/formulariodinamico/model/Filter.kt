package com.nathannicolau.formulariodinamico.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.query.Criteria

open class Filter(
    open val id: String? = null,
    open val dinamicos: Map<String, Any>? = null,
    open val pagina: Int? = null,
    open val tamanho: Int? = null,
) {

    fun montarPageRequest(): PageRequest {
        val pageNumber = (pagina ?: 0).coerceAtLeast(0)
        val pageSize = (tamanho ?: 10).coerceAtLeast(1)
        return PageRequest.of(pageNumber, pageSize)
    }

    fun validarPaginacao() {
        pagina?.let {
            require(it >= 0) { "O valor de página deve ser maior que 0" }
        }

        //Tratamos a lógica de retornar somente 5 registros caso não informado
        tamanho?.let {
            if (it <= 0) {
               it + 5
            }
        }
    }

}