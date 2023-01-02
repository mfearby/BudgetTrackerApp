package com.marcfearby.util

import com.marcfearby.model.ExpensesEntryTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

var LOG_TO_CONSOLE = false

fun newTransaction(): Transaction = TransactionManager.currentOrNew(
    Connection.TRANSACTION_SERIALIZABLE
).apply {
    if (LOG_TO_CONSOLE) addLogger(StdOutSqlLogger)
}

fun enableConsoleLogger() {
    LOG_TO_CONSOLE = true
}

fun createTables() {
    with(newTransaction()) {
        SchemaUtils.create(ExpensesEntryTable)
    }
}

// Execute the command, then commit and close it
fun <T> execute(command: () -> T): T {
    with(newTransaction()) {
        return command().apply {
            commit()
            close()
        }
    }
}