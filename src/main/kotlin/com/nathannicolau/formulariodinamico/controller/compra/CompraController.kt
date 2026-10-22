package com.nathannicolau.formulariodinamico.controller.compra

import com.nathannicolau.formulariodinamico.model.FormularioResponse
import com.nathannicolau.formulariodinamico.model.compra.Compra
import com.nathannicolau.formulariodinamico.model.compra.CompraFilter
import com.nathannicolau.formulariodinamico.model.compra.CompraUpdateFilter
import com.nathannicolau.formulariodinamico.service.compra.CompraService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController(value = "formulario-compras")
@RequestMapping("/api/compras")
class CompraController(
    val compraService: CompraService
) {

    @PostMapping("cadastrar")
    fun cadastrarCompra(@RequestBody compra: Compra): FormularioResponse<Compra> {
        return try {
            FormularioResponse.sucesso(mensagem = "Compra cadastrada com sucesso: ${compraService.cadastrarCompra(compra).id}")
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro ao cadastrar a compra: ${e.message}")
        }
    }

    @PostMapping("cadastrarEmLote")
    fun cadastrarComprasEmLote(@RequestBody compras: List<Compra>): FormularioResponse<Compra> {
        return try {
            FormularioResponse.data(mensagem = "Compras cadastradas com sucesso", data = compraService.cadastrarCompras(compras))
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro ao cadastrar as compras: ${e.message}")
        }
    }

    @PostMapping("recuperar")
    fun recuperarCompras(@RequestBody compraFilter: CompraFilter): FormularioResponse<Compra> {
        return try {
            val paginaCompras = compraService.recuperarCompras(compraFilter)
            val mensagem = if (paginaCompras.data.isEmpty()) {
                "Nenhuma compra encontrada com os filtros informados"
            } else {
                "${paginaCompras.data.size} Compra(s) encontrada(s)"
            }
            FormularioResponse.data(mensagem = mensagem, pagina = paginaCompras)
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro na recuperação das compras: ${e.message}")
        }
    }

    @PutMapping("atualizar")
    fun atualizarCompra(@RequestBody compraUpdateFilter: CompraUpdateFilter): FormularioResponse<Compra> {
        return try {
            FormularioResponse.data(mensagem = "Compra atualizada com sucesso", data = listOf(compraService.atualizarCompra(compraUpdateFilter)))
        } catch (e: Exception) {
            FormularioResponse.erro(mensagem = "Ocorreu um erro ao atualizar a compra: ${e.message}")
        }
    }

}
