package com.leo.support.utils;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/17
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class SocketUtils {

    private static final String TAG = SocketUtils.class.getSimpleName();

    private WebSocketServer webSocketServer;

    public void openWebSocket() {
        Log.e(TAG, "openWebSocket");
        webSocketServer = new WebSocketServer(new InetSocketAddress(9007)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                Log.e(TAG, "onOpen");
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                Log.e(TAG, "onClose");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                Log.e(TAG, String.format("onMessage    message = %s", message));
            }

            @Override
            public void onMessage(WebSocket conn, ByteBuffer message) {
                super.onMessage(conn, message);
                Log.e(TAG, String.format("onMessage    ByteBuffer    message长度 = %s", message.remaining()));
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                Log.e(TAG, String.format("onError    Exception = %s", ex.getMessage()));
            }

            @Override
            public void onStart() {
                Log.e(TAG, "onStart");
            }
        };
        webSocketServer.start();
    }
}
