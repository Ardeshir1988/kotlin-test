package com.example.kotlintest.mockito

import com.example.kotlintest.QuoteEntity
import com.example.kotlintest.toDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant

class UnitTestWithoutContext {
    @Test
    fun `test toDto`(){
        val quote = QuoteEntity(id = 1, price = 99, isin = "i1", date = Instant.now())
        val quoteDto = quote.toDto()
        Assertions.assertEquals(99L,quoteDto.price)
    }
}