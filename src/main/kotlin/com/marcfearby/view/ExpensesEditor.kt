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
                        datepicker(model.entryDate) {
                            this.required()
                            validator {
                                when {
                                    it?.dayOfMonth.toString().isEmpty()
                                            || it?.monthValue.toString().isEmpty()
                                            || it?.year.toString().isEmpty() -> error("Date is required")
                                    else -> null
                                }
                            }
                        }
                    }
                    field("Item name") {
                        maxWidth = 220.0
                        textfield(model.itemName) {
                            this.required()
                            validator {
                                when {
                                    it.isNullOrEmpty() -> error("Item name is required")
                                    it.length < 3 -> error("Item name is too short")
                                    else -> null
                                }
                            }
                        }
                    }
                    field("Item price") {
                        maxWidth = 220.0
                        textfield(model.itemPrice) {
                            this.required()
                            validator {
                                when(it) {
                                    null -> error("Item price is required")
                                    else -> null
                                }
                            }
                        }
                    }
                }

                hbox(10.0) {
                    button("Add Item") {
                        enableWhen(model.valid)
                        action {
                            model.commit {
                                addItem()
                                model.rollback()
                            }
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