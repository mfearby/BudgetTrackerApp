package com.marcfearby.view

import tornadofx.*

class Styles: Stylesheet() {
    companion object {
        val mainView by cssclass()
    }

    init {
        mainView {
            backgroundColor += c("EBEBEB")
        }
    }
}