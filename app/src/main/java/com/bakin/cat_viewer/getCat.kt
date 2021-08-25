package com.bakin.cat_viewer

import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.IndexOutOfBoundsException
import java.net.HttpURLConnection
import java.net.URL


object Cat {

    private const val CAT_ERROR_URL = "https://im0-tub-ru.yandex.net/i?id=51c92e4e9b4e2f09e23e6aa1cab293ad&n=13"

    private fun getJson(): String {
        var connection: HttpURLConnection? = null
        try {
            connection =
                URL("https://api.thecatapi.com/v1/images/search").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 2500
            connection.readTimeout = 2500
            connection.connect()
            if (connection.responseCode == 200) {
                val input = BufferedInputStream(connection.inputStream)
                val reader = BufferedReader(InputStreamReader(input))
                return reader.readText()
            }
        } catch (e: IOException) {
            Log.e("TAG","Bad request : $e")
        } finally {
            connection?.disconnect()
        }
        return "error"
    }

    fun getUrl(): String {

        fun String.parsed(): String {
            val list = this.split("\"")
            return try {list[list.size - 6]} catch (e: IndexOutOfBoundsException) {"error"}
        }

        val content = getJson().parsed()
        Log.d("TAG",content)

        return if (content == "error") CAT_ERROR_URL
        else content
    }
}