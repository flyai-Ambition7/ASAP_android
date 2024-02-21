package com.asap.asap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request

class BackgroundTaskActivity : AppCompatActivity() {

    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_task)

        client = OkHttpClient()
        // 서버 url
        val request: Request = Request.Builder()
            .url("wss://pubwss.bithumb.com/pub/ws")
            .build()
        val listener: WebSocketListener = WebSocketListener()

        client.newWebSocket(request, listener)
        client.dispatcher().executorService().shutdown()
    }
}