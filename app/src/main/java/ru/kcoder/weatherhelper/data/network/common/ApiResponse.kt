package ru.kcoder.weatherhelper.data.network.common

interface ApiResponse {
    var cod: String?

    companion object {
        const val OK_RESPONSE = "200"
    }
}