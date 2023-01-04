package com.marcfearby.view

import javafx.stage.Stage
import tornadofx.*

class BudgetTracker: App(BudgetTrackerWorkspace::class, Styles::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 700.0
            height = 480.0
        }
        super.start(stage)
    }
}