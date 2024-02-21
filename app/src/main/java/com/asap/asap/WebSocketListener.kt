package com.asap.asap

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketListener : WebSocketListener() {

    /*

webSocket.send()를 통해 서버로 메시지를 보낸다.
override fun onMessage()를 통해 서버로부터 메시지를 받는다.
    * */
    override fun onOpen(webSocket: WebSocket, response: Response?) {
        webSocket.send("{\"type\":\"ticker\", \"symbols\": [\"BTC_KRW\"], \"tickTypes\": [\"30M\"]}")
        Log.d("아아아앙아ㅏㅏ", "이이이이");
        webSocket.close(NORMAL_CLOSURE_STATUS, null) //없을 경우 끊임없이 서버와 통신함
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        Log.d("Socket","Receiving : $text")
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
        Log.d("Socket", "Receiving bytes : $bytes")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("Socket","Closing : $code / $reason")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        webSocket.cancel()
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
        Log.d("Socket","Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}