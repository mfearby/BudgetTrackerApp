package com.marcfearby.view

import com.marcfearby.model.ExpensesEntryModel
import javafx.scene.layout.BorderPane
import tornadofx.*

class ExpensesEditor: View("Expenses") {

    val model = ExpensesEntryModel()

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
                        action {  }
                    }
                    button("Delete Item") {
                        action {  }
                    }
                    button("Reset") {
                        action {  }
                    }
                }
            }
        }
    }

}