package com.marcfearby.controller

import com.marcfearby.model.ExpensesEntry
import com.marcfearby.model.ExpensesEntryModel
import com.marcfearby.model.ExpensesEntryTable
import com.marcfearby.model.toExpensesEntry
import com.marcfearby.util.execute
import com.marcfearby.util.toDateTime
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.math.BigDecimal
import java.time.LocalDate

class ItemController: Controller() {

    private val listOfItems: ObservableList<ExpensesEntryModel> = execute {
        ExpensesEntryTable.selectAll().map {
            ExpensesEntryModel().apply {
                item = it.toExpensesEntry()
            }
        }.asObservable()
    }

    var items: ObservableList<ExpensesEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
//        add(LocalDate.now(), "Bananas", 4.95)
//        add(LocalDate.now(), "Milk", 3.00)

        items = listOfItems

        listOfItems.forEach {
            println("Item Name: ${it.itemName} (${'$'}${it.itemPrice})")
            pieItemsData.add(PieChart.Data(
                it.itemName.value, it.itemPrice.value
            ))
        }
    }

    fun add(entryDate: LocalDate, itemName: String, itemPrice: Double): ExpensesEntry {
        val newRow = execute {
            ExpensesEntryTable.insert {
                it[ExpensesEntryTable.entryDate] = entryDate.toDateTime()
                it[ExpensesEntryTable.itemName] = itemName
                it[ExpensesEntryTable.itemPrice] = BigDecimal.valueOf(itemPrice)
            }
        }

        val newEntry = ExpensesEntry(newRow[ExpensesEntryTable.id], entryDate, itemName, itemPrice)
        listOfItems.add(ExpensesEntryModel().apply {
            item = newEntry
        })
        pieItemsData.add(PieChart.Data(itemName, itemPrice))
        return newEntry
    }

    fun update(item: ExpensesEntryModel): Int {
        return execute {
            ExpensesEntryTable.update({ ExpensesEntryTable.id eq(item.id.value) }) {
                it[entryDate] = item.entryDate.value.toDateTime()
                it[itemName] = item.itemName.value
                it[itemPrice] = BigDecimal.valueOf(item.itemPrice.value)
            }
        }
    }

    fun delete(item: ExpensesEntryModel): Int {
        listOfItems.remove(item)
        removeItemFromPieChart(item)
        return execute {
            ExpensesEntryTable.deleteWhere { ExpensesEntryTable.id eq(item.id.value) }
        }
    }

    private fun removeItemFromPieChart(item: ExpensesEntryModel) {
        val index = pieItemsData.indexOfFirst {
            it.name == item.itemName.value && it.pieValue == item.itemPrice.value
        }
        if (index >= 0) pieItemsData.removeAt(index)
    }

    fun updatePiePiece(piece: ExpensesEntryModel) {
        val index = items.indexOfFirst {
            piece.id == it.id
        }
        if (index >= 0) {
            pieItemsData[index].let {
                it.name = piece.itemName.value
                it.pieValue = piece.itemPrice.value
            }
        }
    }

}



















































