package com.example.kotlintest.mockito

import com.example.kotlintest.DownService
import com.example.kotlintest.HighService
import com.example.kotlintest.QuoteRepo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
class IntegrationHighServiceTest {
    @Autowired
    lateinit var quoteRepo: QuoteRepo

    @Autowired
    lateinit var downService: DownService

    @Autowired
    lateinit var highService: HighService
    @BeforeEach
    fun deleteAll(){
        quoteRepo.deleteAll()
    }
    @Test
    fun `test findAllQuotesByIsinAndDate`(){
        val now = Instant.now()
        downService.saveQuote(price = 10L, isin = "isin1")


        val quotesFromService =   highService.findAllQuotesByIsinAndDate(isin = "isin1", from = now.minusSeconds(60*60), to = now.plusSeconds(60*60))
        val quotesFromRepo =   quoteRepo.findAllByIsinAndDateBetween(isin = "isin1", from = now.minusSeconds(60*60), to = now.plusSeconds(60*60))

        Assertions.assertEquals(1,quotesFromService.size)
        Assertions.assertEquals(1,quotesFromRepo.size)
    }
}