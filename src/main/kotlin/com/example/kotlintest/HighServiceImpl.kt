package com.example.kotlintest

import org.springframework.stereotype.Service
import java.time.Instant

@Service
class HighServiceImpl(private val downService: DownService):HighService {

    override fun findAllQuotesByIsinAndDate(isin:String,from: Instant,to:Instant):List<QuoteEntity>{
        return downService.getQuotesByDate(isin = isin, from = from, to = to)
    }
}
interface HighService{
    fun findAllQuotesByIsinAndDate(isin:String,from: Instant,to:Instant):List<QuoteEntity>
}
