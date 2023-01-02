package com.marcfearby.view

import tornadofx.*

class Styles: Stylesheet() {
    companion object {
        val mainWorkspace by cssclass()
    }

    init {
        mainWorkspace {
            backgroundColor += c("EBEBEB")
        }
    }
}