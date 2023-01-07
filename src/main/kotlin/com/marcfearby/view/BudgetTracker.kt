package com.marcfearby.view

import javafx.stage.Stage
import tornadofx.*

class BudgetTracker: App(BudgetTrackerWorkspace::class, Styles::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 725.0
            height = 550.0
        }
        super.start(stage)
    }
}