package ru.kcoder.weatherhelper.data.network.common

interface ApiResponse {
    var cod: Int?

    companion object {
        const val OK_RESPONSE = 200
    }
}