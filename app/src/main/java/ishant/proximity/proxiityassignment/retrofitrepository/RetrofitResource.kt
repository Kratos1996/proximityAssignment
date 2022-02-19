package com.actorpay.merchant.repositories.retrofitrepository
/*
* © Copyright Ishant Sharma
* Android Developer
* JAVA/KOTLIN
* */
sealed class RetrofitResource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T?) : RetrofitResource<T>(data, "")
    class Error<T>(message: String?) : RetrofitResource<T>(null, message)
}