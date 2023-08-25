package com.example.kotlintest

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class QuoteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?=null,
    val price: Long,
    val isin: String,
    val date: Instant
)

data class QuoteDto(val price: Long,val isin: String,val date:LocalDateTime)
fun QuoteEntity.toDto() = QuoteDto(price = this.price,isin = this.isin,date = LocalDateTime.ofInstant(date, ZoneId.systemDefault()))
@Repository
interface QuoteRepo:JpaRepository<QuoteEntity,Long> {
    fun findAllByIsinAndDateBetween(isin:String,from:Instant,to:Instant):List<QuoteEntity>
}