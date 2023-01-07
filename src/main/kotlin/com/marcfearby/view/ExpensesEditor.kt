package com.marcfearby.view

import com.marcfearby.controller.ItemController
import com.marcfearby.model.ExpensesEntryModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import tornadofx.*

class ExpensesEditor: View("Expenses") {

    private val controller: ItemController by inject()
    private val model = ExpensesEntryModel()
    var mTableView: TableViewEditModel<ExpensesEntryModel> by singleAssign()
    private val totalExpensesProperty = SimpleDoubleProperty(0.0)
    var totalExpensesLabel: Label by singleAssign()

    init {
        updateTotalExpenses()
    }

    override val root: BorderPane = borderpane {
        center = vbox {
            form {
                fieldset {
                    field("Entry date") {
                        maxWidth = 220.0
                        datepicker(model.entryDate) {
                            this.required()
                            // This validator doesn't work if the user changes the date via the keyboard :-(
                            validator {
                                when {
                                    it?.dayOfMonth.toString().isEmpty()
                                            || it?.monthValue.toString().isEmpty()
                                            || it?.year.toString().isEmpty() -> error("Date is required")
                                    else -> null
                                }
                            }
                            setOnKeyPressed {
                                invokeAddItem(it)
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
                            setOnKeyPressed {
                                invokeAddItem(it)
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
                        setOnKeyPressed {
                            invokeAddItem(it)
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
                        action {
                            mTableView.tableView.selectedItem?.let {
                                controller.delete(it)
                                totalExpensesProperty.value -= it.itemPrice.value
                                updateTotalExpenses()
                            }
                        }
                    }
                    button("Reset") {
                        action {  }
                    }
                }

                fieldset {
                    tableview {
                        items = controller.items
                        mTableView = editModel
                        column("ID", ExpensesEntryModel::id)
                        column("Added", ExpensesEntryModel::entryDate).makeEditable()
                        column("Item name", ExpensesEntryModel::itemName).makeEditable()
                        column("Item price", ExpensesEntryModel::itemPrice).makeEditable()

                        onEditCommit {
                            controller.update(it)
                            controller.updatePiePiece(it)
                        }
                    }
                }
            }
        }
        right = vbox {
            maxWidth = 400.0
            alignment = Pos.CENTER
            piechart {
                data = controller.pieItemsData
            }
            totalExpensesLabel = label {
                if (totalExpensesProperty.value != 0.0) {
                    bind(
                        Bindings.concat(
                            "Total Expenses: $",
                            Bindings.format("%.2f", totalExpensesProperty)
                        )
                    )
                }
            }
        }
    }

    private fun invokeAddItem(event: KeyEvent) {
        if (event.code == KeyCode.ENTER) {
            model.commit {
                addItem()
                model.rollback()
            }
        }
    }

    private fun addItem() {
        with(model) {
            controller.add(entryDate.value, itemName.value, itemPrice.value)
        }
    }

    private fun updateTotalExpenses() {
        val total: Double = try {
            controller.items.sumOf { it.itemPrice.value }
        } catch (_: Exception) {
            0.0
        }
        totalExpensesProperty.set(total)
        model.totalExpenses.value = total
    }

}