package com.nathannicolau.formulariodinamico.repository.compra

import com.nathannicolau.formulariodinamico.model.compra.Compra
import com.nathannicolau.formulariodinamico.model.compra.CompraFilter
import com.nathannicolau.formulariodinamico.model.compra.CompraUpdateFilter

interface CompraRepositoryCustom {

    fun buscarDinamicamente(compraFilter: CompraFilter): List<Compra>

    fun atualizarCompra(compraUpdateFilter: CompraUpdateFilter): Compra

}
