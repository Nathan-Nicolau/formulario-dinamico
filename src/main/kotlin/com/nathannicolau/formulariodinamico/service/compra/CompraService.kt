package com.nathannicolau.formulariodinamico.service.compra

import com.nathannicolau.formulariodinamico.model.Pagina
import com.nathannicolau.formulariodinamico.model.compra.Compra
import com.nathannicolau.formulariodinamico.model.compra.CompraFilter
import com.nathannicolau.formulariodinamico.model.compra.CompraUpdateFilter
import com.nathannicolau.formulariodinamico.repository.compra.CompraRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CompraService(
    val compraRepository: CompraRepository
) {

    fun cadastrarCompra(compra: Compra): Compra {
        compra.validarCadastro()
        return compraRepository.save(compra)
    }

    fun cadastrarCompras(compras: List<Compra>): List<Compra> {
        compras.forEach { compra -> compra.validarCadastro() }
        return compraRepository.saveAll(compras)
    }

    fun recuperarCompras(compraFilter: CompraFilter): Pagina<Compra> {
        compraFilter.validarPaginacao()
        val compras = compraRepository.buscarDinamicamente(compraFilter)
        return Pagina.comCalculo(
            numeroPagina = compraFilter.pagina ?: 1,
            tamanhoPagina = compraFilter.tamanho ?: compras.size,
            totalRegistros = compras.size,
            data = compras
        )
    }

    fun atualizarCompra(compraUpdateFilter: CompraUpdateFilter): Compra {
        compraUpdateFilter.validarAtualizacao()
        return compraRepository.atualizarCompra(compraUpdateFilter)
    }

}
