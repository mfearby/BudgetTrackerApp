package com.marcfearby.view

import com.marcfearby.model.ExpensesEntryTable
import com.marcfearby.util.createTables
import com.marcfearby.util.enableConsoleLogger
import com.marcfearby.util.execute
import com.marcfearby.util.toDateTime
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import tornadofx.*
import java.math.BigDecimal
import java.time.LocalDate

class BudgetTrackerWorkspace: Workspace("Budget Tracker Workspace", NavigationMode.Tabs) {

    init {
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./budget-tracker.db", "org.sqlite.JDBC")
        createTables()

        execute {
            ExpensesEntryTable.insert {
                it[entryDate] = LocalDate.now().toDateTime()
                it[itemName] = "Candy"
                it[itemPrice] = BigDecimal.valueOf(5.95)
            }
        }

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}