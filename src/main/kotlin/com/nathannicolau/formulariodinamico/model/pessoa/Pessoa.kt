package com.nathannicolau.formulariodinamico.model.pessoa

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

/**
 * A ideia é que essa classe represente a collection(tabela) de registro de Pessoas(documentos)
 * utilizamos aqui a anotação Document do MongoDB; o Id vem do próprio Spring, mas serve para como atributo único de identificação
 *
 * Alguns campos são diretamente definidos como "fixos", sendo que todas as pessoas terão
 * Posteriormente, temos o campo dinâmicos, permitindo o registro de qualquer informação, numa estrutura de Map
 *
 * */

@Document(collection = "pessoas")
data class Pessoa(
    @Id
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val dinamicos: Map<String, Any> = emptyMap()
): Serializable
