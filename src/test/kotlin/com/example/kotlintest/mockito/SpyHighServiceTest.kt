package com.example.kotlintest.mockito

import com.example.kotlintest.DownService
import com.example.kotlintest.HighService
import com.example.kotlintest.QuoteEntity
import com.example.kotlintest.QuoteRepo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import java.time.Instant

@SpringBootTest
class SpyHighServiceTest {


    @SpyBean
    lateinit var downService: DownService
    @Autowired
    lateinit var quoteRepo: QuoteRepo
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

    @Test
    fun `test findAllQuotesByIsinAndDate spy`(){
        val now = Instant.now()
        Mockito.`when`(downService.getQuotesByDate(isin = "isin1", from = now.minusSeconds(60*60),to = now.plusSeconds(60*60)))
            .thenReturn(listOf(QuoteEntity(id = 1, price = 10, isin = "isin1", date =now)))


        val quotesFromService =   highService.findAllQuotesByIsinAndDate(isin = "isin1", from = now.minusSeconds(60*60), to = now.plusSeconds(60*60))
        val quotesFromRepo =   quoteRepo.findAllByIsinAndDateBetween(isin = "isin1", from = now.minusSeconds(60*60), to = now.plusSeconds(60*60))
        Assertions.assertEquals(1,quotesFromService.size)
        Assertions.assertEquals(10L,quotesFromService[0].price)
        Assertions.assertEquals(0,quotesFromRepo.size)

        downService.saveQuote(price = 7L, isin = "isin1")
        val quotesFromRepoAfterSave =   quoteRepo.findAllByIsinAndDateBetween(isin = "isin1", from = now.minusSeconds(60*60), to = now.plusSeconds(60*60))

        Assertions.assertEquals(1,quotesFromRepoAfterSave.size)
        Assertions.assertEquals(7L,quotesFromRepoAfterSave[0].price)

    }

}