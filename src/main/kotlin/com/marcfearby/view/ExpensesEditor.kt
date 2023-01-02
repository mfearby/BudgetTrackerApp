package com.marcfearby.view

import com.marcfearby.controller.ItemController
import com.marcfearby.model.ExpensesEntryModel
import javafx.scene.layout.BorderPane
import tornadofx.*

class ExpensesEditor: View("Expenses") {

    private val controller: ItemController by inject()
    private val model = ExpensesEntryModel()

    override val root: BorderPane = borderpane {
        center = vbox {
            form {
                fieldset {
                    field("Entry date") {
                        maxWidth = 220.0
                        datepicker(model.entryDate)
                    }
                    field("Item name") {
                        maxWidth = 220.0
                        textfield(model.itemName)
                    }
                    field("Item price") {
                        maxWidth = 220.0
                        textfield(model.itemPrice)
                    }
                }

                hbox(10.0) {
                    button("Add Item") {
                        action {
                            addItem()
                        }
                    }
                    button("Delete Item") {
                        action {  }
                    }
                    button("Reset") {
                        action {  }
                    }
                }

                fieldset {
                    tableview<ExpensesEntryModel> {
                        items = controller.items
                        column("ID", ExpensesEntryModel::id)
                        column("Added", ExpensesEntryModel::entryDate)
                        column("Item name", ExpensesEntryModel::itemName)
                        column("Item price", ExpensesEntryModel::itemPrice)
                    }
                }
            }
        }
    }

    private fun addItem() {
        with(model) {
            controller.add(entryDate.value, itemName.value, itemPrice.value)
        }
    }

}