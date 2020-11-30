@file:Suppress("DEPRECATION")

package com.example.sportgameprototype

import android.os.AsyncTask
import android.util.Log
import org.json.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class HTTPtask : AsyncTask<String, Int, String>() {
    lateinit var sendMsg : String
    override fun doInBackground(vararg params: String?): String? {
        val input_url: String? = params[0]!!

        try {
            val url = URL(input_url)
            val urlConnection = url.openConnection() as HttpURLConnection

            urlConnection.requestMethod = "POST"
            urlConnection.doOutput = true

            val osw = OutputStreamWriter(urlConnection.outputStream)
            // type 경우 별로 sendMsg 수정해야함.
            when (params[1]){
                "spinner" -> {
                    sendMsg = "type=" + params[1]
                }
                "recycle" -> {
                    sendMsg = "type=" + params[1] +
                            "&season=" + params[2] +
                            "&team=" + params[3]
                }
                "gamelist" -> {
                    sendMsg = "type=" + params[1] +
                            "&date=" + params[2]
                }
                "player" -> {
                    sendMsg = "type=${params[1]}&pid=${params[2]}&info=${params[3]}"
                }
                "query" -> {
                    sendMsg = "type=${params[1]}&sql=${params[2]}"
                }
                else -> {
                    return "HTTP Request InputType Error"
                }
            }
            Log.d("USERLOG-HTTPTASK", "sendMsg : ${sendMsg}")
            osw.write(sendMsg)
            osw.flush()

            if(urlConnection.responseCode == HttpURLConnection.HTTP_OK){
                val streamReader = InputStreamReader(urlConnection.inputStream)
                val buffered = BufferedReader(streamReader)
                val content = StringBuilder()

                while (true) {
                    val line = buffered.readLine() ?: break
                    content.append(line)
                }
                buffered.close()
                urlConnection.disconnect()
                println(content.toString())
                return content.toString()
            }
            else{
                return "Connection Error"
            }
        } catch (e: Exception) {
            return "Error" + e.message
        }
    }
}