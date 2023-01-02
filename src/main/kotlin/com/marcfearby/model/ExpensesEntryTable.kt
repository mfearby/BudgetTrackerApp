package com.marcfearby.model

import com.marcfearby.util.toJavaLocalDate
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.math.BigDecimal
import java.time.LocalDate
import tornadofx.*

// "object" in Kotlin is like a singleton (declares and creates one instance only at the same time)
object ExpensesEntryTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val entryDate: Column<DateTime> = date("entry_date")
    val itemName: Column<String> = varchar("name", length = 50)
    val itemPrice: Column<BigDecimal> = decimal("price", scale = 2, precision = 9)
}

class ExpensesEntry(id: Int, entryDate: LocalDate, itemName: String, itemPrice: Double) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val entryDateProperty = SimpleObjectProperty(entryDate)
    var entryDate by entryDateProperty

    val itemNameProperty = SimpleStringProperty(itemName)
    var itemName by itemNameProperty

    val itemPriceProperty = SimpleDoubleProperty(itemPrice)
    var itemPrice by itemPriceProperty

    override fun toString(): String {
        return "ExpensesEntry(id=$id, entryDate=$entryDate, itemName=$itemName, itemPrice=$itemPrice)"
    }
}

class ExpensesEntryModel: ItemViewModel<ExpensesEntry>() {
    val id = bind { item?.idProperty }
    val entryDate = bind { item?.entryDateProperty }
    val itemName = bind { item?.itemNameProperty}
    val itemPrice = bind { item?.itemPriceProperty}
}

fun ResultRow.toExpensesEntry() = ExpensesEntry(
    this[ExpensesEntryTable.id],
    this[ExpensesEntryTable.entryDate].toJavaLocalDate(),
    this[ExpensesEntryTable.itemName],
    this[ExpensesEntryTable.itemPrice].toDouble()
)