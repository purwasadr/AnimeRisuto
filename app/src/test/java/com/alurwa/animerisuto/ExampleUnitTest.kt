package com.alurwa.animerisuto

import com.alurwa.animerisuto.model.IItemForList
import com.alurwa.animerisuto.model.ItemCoba
import com.alurwa.animerisuto.model.ItemCoba2
import com.alurwa.animerisuto.model.ItemCoba3
import com.alurwa.animerisuto.model.ItemForList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.concurrent.TimeUnit

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

    fun isGanjil(a: Int): Boolean = a % 2 != 0

    @Test
    fun dataClass_equalTest() {
        val data1: IItemForList = ItemCoba3(1, 2, "Eksecalibar")
        val data2: IItemForList = ItemCoba3(1, 3, "Eksecalibar")

        println(data1 == data2)
    }

    @Test
    fun interfaceTest() {
        val data1 = ItemCoba3(3, 2, "AWokwokwokwokow")
        val toData2 = data1 as IItemForList
        val toData3 = toData2 as ItemCoba3

        println(toData3)
        println(toData3 as ItemCoba2)
    }

    @Test
    fun stateFlowTest() {
        TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        val stateFlow = MutableStateFlow(0)

        for (a: Int in 0..5) {
            stateFlow.value = a
        }

        runBlocking {
            stateFlow.collect {
                println(it)
            }

        }
    }
}
