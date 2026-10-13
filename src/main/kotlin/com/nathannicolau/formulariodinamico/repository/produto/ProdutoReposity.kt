package com.nathannicolau.formulariodinamico.repository.produto

import com.nathannicolau.formulariodinamico.model.produto.Produto
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoReposity : MongoRepository<Produto, String>, ProdutoRepositoryCustom