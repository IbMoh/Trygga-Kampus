package com.example.tryggakampus.data.model

data class NetworkError(
    val error: NetworkErr,
    val throwable: Throwable? = null
)

enum class NetworkErr(val message: String){
    NoConnection("No Internet Connection Available"),
    UnknownResponse("An Unknown Response Has Occurred"),
    UnknownError("An Unknown Error Has Occurred")
}