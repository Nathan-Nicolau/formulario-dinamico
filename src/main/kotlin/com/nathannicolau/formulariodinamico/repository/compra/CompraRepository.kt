package com.nathannicolau.formulariodinamico.repository.compra

import com.nathannicolau.formulariodinamico.model.compra.Compra
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CompraRepository : MongoRepository<Compra, String>, CompraRepositoryCustom
