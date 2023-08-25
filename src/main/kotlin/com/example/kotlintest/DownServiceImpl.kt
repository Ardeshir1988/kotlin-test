package com.example.kotlintest

import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DownServiceImpl(private val quoteRepo: QuoteRepo):DownService {

    override fun saveQuote(price: Long, isin: String): QuoteEntity {
        return quoteRepo.save(QuoteEntity(price = price, isin = isin, date = Instant.now()))
    }


    override fun getQuotesByDate(isin: String, from: Instant, to: Instant): List<QuoteEntity> {
        return quoteRepo.findAllByIsinAndDateBetween(isin = isin, from = from, to = to)
    }
}

interface DownService{
    fun saveQuote(price: Long, isin: String): QuoteEntity
    fun getQuotesByDate(isin: String, from: Instant, to: Instant): List<QuoteEntity>
}