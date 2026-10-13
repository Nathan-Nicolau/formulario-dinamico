package com.nathannicolau.formulariodinamico.model.produto

import com.nathannicolau.formulariodinamico.model.ValidadorDocumento
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.math.BigDecimal

@Document(collection = "produtos")
data class Produto(
    @Id
    val id: String? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val valor: BigDecimal? = null,
    val dinamicos: Map<String, Any> = emptyMap()
): ValidadorDocumento, Serializable {

    override fun validarCadastro() {
        require(nome != null) { "O nome deve ser informado para cadastro" }
        require(descricao != null) { "A descrição deve ser informada para cadastro" }
        require(valor != null) { "O valor deve ser informado para cadastro" }
    }

}
