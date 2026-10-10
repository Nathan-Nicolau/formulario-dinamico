package com.nathannicolau.formulariodinamico.repository

import com.nathannicolau.formulariodinamico.model.Pessoa
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PessoaRepository : MongoRepository<Pessoa, String>, PessoaRepositoryCustom {

    // O Spring Data fornecerá o findAll(), save(), findById() vindos do MongoRepository
    // E também o buscarComFiltroDinamico() vindo do PessoaRepositoryCustom

    // Você ainda pode adicionar Query Methods simples aqui se precisar:
    fun findByEmail(email: String): Pessoa?
}