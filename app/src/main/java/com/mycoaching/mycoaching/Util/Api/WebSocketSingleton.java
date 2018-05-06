package com.mycoaching.mycoaching.Util.Api;

import android.util.Log;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


/**
 * Created by kevin on 06/05/2018.
 */
public class WebSocketSingleton {

    private WebSocketSingleton(){

    }

    public static WebSocket getInstance() {
        return SingletonHolder.getInstance();
    }

    private static class SingletonHolder {
        public final static WebSocket getInstance(){
            Request request = new Request.Builder().url("ws://212.47.234.147/ws?id=2").build();
            return OkHttpSingleton.getInstance().newWebSocket(request, new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    super.onOpen(webSocket, response);
                    Log.i("TEST WS :", response.toString());
                }
            });
        }
    }
}
