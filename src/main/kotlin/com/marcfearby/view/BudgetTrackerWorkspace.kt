package com.marcfearby.view

import com.marcfearby.controller.ItemController
import com.marcfearby.util.createTables
import com.marcfearby.util.enableConsoleLogger
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class BudgetTrackerWorkspace: Workspace("Budget Tracker Workspace", NavigationMode.Tabs) {

    init {
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./budget-tracker.db", "org.sqlite.JDBC")
        createTables()

        ItemController()

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}