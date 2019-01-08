package ru.kcoder.weatherhelper.data.network.common

interface ApiResponseFuture {
    var cod: String?

    companion object {
        const val OK_RESPONSE = "200"
    }
}