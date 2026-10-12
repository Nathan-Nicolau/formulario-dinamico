package com.nathannicolau.formulariodinamico.model

open class Filter(
    open val id: String? = null,
    open val dinamicos: Map<String, Any>? = null,
    open val pagina: Int? = null,
    open val tamanho: Int? = null,
) {

    fun possuiPaginacao() = pagina != null && tamanho != null

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