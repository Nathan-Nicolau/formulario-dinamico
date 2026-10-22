package com.nathannicolau.formulariodinamico.model.compra

import com.nathannicolau.formulariodinamico.model.ValidadorDocumento
import com.nathannicolau.formulariodinamico.model.pessoa.Pessoa
import com.nathannicolau.formulariodinamico.model.produto.Produto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "compra")
data class Compra(
    @Id
    val id: String? = null,
    val nome: String? = null,
    val produtos: List<Produto>? = null,
    val pessoa: Pessoa? = null,
    val dinamicos: Map<String, Any>? = emptyMap()
): ValidadorDocumento, Serializable {

    override fun validarCadastro() {
        require(!nome.isNullOrBlank()) { "O nome deve ser informado para cadastro" }
        require(!produtos.isNullOrEmpty()) { "Os produtos devem ser informados para cadastro" }
        require(pessoa != null) { "A pessoa deve ser informada para cadastro" }
    }

}

