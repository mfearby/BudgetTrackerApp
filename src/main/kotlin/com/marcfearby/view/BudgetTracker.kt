package com.marcfearby.view

import javafx.stage.Stage
import tornadofx.*

class BudgetTracker: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 640.0
            height = 480.0
        }
        super.start(stage)
    }
}