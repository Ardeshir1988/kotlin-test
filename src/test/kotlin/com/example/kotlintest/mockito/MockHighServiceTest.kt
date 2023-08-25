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
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Instant

@SpringBootTest
class MockHighServiceTest {

    @MockBean
    lateinit var downService: DownService

    @Autowired
    lateinit var highService: HighService

    @Autowired
    lateinit var quoteRepo: QuoteRepo
    @BeforeEach
    fun deleteAll(){
        quoteRepo.deleteAll()
    }
    @Test
    fun `test findAllQuotesByIsinAndDate`(){
        val now = Instant.now()
        Mockito.`when`(downService.getQuotesByDate(isin = "isin1", from = now.minusSeconds(60*60),to = now.plusSeconds(60*60)))
            .thenReturn(listOf(QuoteEntity(id = 1, price = 10, isin = "isin1", date =now)))
        val quotesFromService =   highService.findAllQuotesByIsinAndDate(isin = "isin1", from = now.minusSeconds(60*60), to = now.plusSeconds(60*60))
        val quotesFromRepo =   quoteRepo.findAllByIsinAndDateBetween(isin = "isin1", from = now.minusSeconds(60*60), to = now.plusSeconds(60*60))
        Assertions.assertEquals(1,quotesFromService.size)
        Assertions.assertEquals(0,quotesFromRepo.size)
    }
}