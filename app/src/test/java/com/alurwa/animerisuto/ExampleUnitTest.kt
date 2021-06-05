package com.alurwa.animerisuto

import com.alurwa.animerisuto.model.ItemCoba
import com.alurwa.animerisuto.model.ItemCoba2
import com.alurwa.animerisuto.model.ItemForList
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val item1: ItemForList = ItemCoba(1, "sssw", "sssa")
        val wwe: ItemForList = ItemCoba2(1, "sssw", "sssa")

        println(wwe == item1)
    }
}
