package leo.work.support.util;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
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

    private static SocketUtils instance;

    //获取实例
    public static synchronized SocketUtils getInstance() {
        if (instance == null) {
            instance = new SocketUtils();
        }
        return instance;
    }

    private WebSocketServer webSocketServer;
    private WebSocketClient webSocketClient;

    // 服务端 ---- 打开端口
    public void openWebSocket(int port, OnSocketUtilsCallBack callBack) {
        Log.e(TAG, "openWebSocket");
        webSocketServer = new WebSocketServer(new InetSocketAddress(port)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                Log.e(TAG, "WebSocketServer ---- onOpen");
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                Log.e(TAG, "WebSocketServer ---- onClose");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                Log.e(TAG, String.format("WebSocketServer ---- onMessage    message = %s", message));
            }

            @Override
            public void onMessage(WebSocket conn, ByteBuffer byteBuffer) {
                super.onMessage(conn, byteBuffer);
                Log.e(TAG, String.format("WebSocketServer ---- onMessage    ByteBuffer    message长度 = %s", byteBuffer.remaining()));
                callBack.onMessage(byteBuffer);
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                Log.e(TAG, String.format("WebSocketServer ---- onError    Exception = %s", ex.getMessage()));
            }

            @Override
            public void onStart() {
                Log.e(TAG, "WebSocketServer ---- onStart");
            }
        };
        webSocketServer.start();
    }

    // 客户端 ---- 连接服务端
    public void clientConnect(String url, OnSocketUtilsCallBack callBack) {
        try {
            webSocketClient = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.e(TAG, "WebSocketClient ---- onOpen");
                }

                @Override
                public void onMessage(String message) {
                    Log.e(TAG, String.format("WebSocketClient ---- onMessage    message = %s", message));
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    super.onMessage(bytes);
                    Log.e(TAG, String.format("WebSocketClient ---- onMessage    ByteBuffer    bytes长度 = %s", bytes.remaining()));
                    callBack.onMessage(bytes);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.e(TAG, String.format("WebSocketClient ---- onClose    code = %s    reason = %s    remote = %s", code, reason, remote));
                    clientReconnect();
                }

                @Override
                public void onError(Exception ex) {
                    Log.e(TAG, String.format("WebSocketClient ---- onError    Exception = %s", ex.getMessage()));
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            Log.e(TAG, String.format("URISyntaxException    e = %s", e.getMessage()));
        }
    }

    // 客户端 ---- 重连
    public void clientReconnect() {
        if (webSocketClient == null) {
            return;
        }
        webSocketClient.reconnect();
    }

    // 服务端发送
    public void serverSend(byte[] bytes) {
        if (webSocketServer == null) {
            Log.i(TAG, "serverSend    发送拦截");
            return;
        }
        //webSocketServer.broadcast(bytes);
        for (WebSocket webSocket : webSocketServer.getConnections()) {
            if (!webSocket.isOpen()) {
                continue;
            }
            Log.i(TAG, String.format("serverSend    发送数据    bytes = %s", bytes.length));
            webSocket.send(bytes);
        }
    }

    // 客户端 ---- 发送数据
    public void clientSend(byte[] bytes) {
        if (!clientCanSend()) {
            Log.i(TAG, "clientSend    发送拦截");
            return;
        }
        Log.i(TAG, String.format("clientSend    发送数据    bytes = %s", bytes.length));
        webSocketClient.send(bytes);
    }

    // 客户端 ---- 是否能发送
    public boolean clientCanSend() {
        if (webSocketClient == null) {
            Log.i(TAG, "发送拦截1");
            return false;
        }
        if (webSocketClient.isClosing()) {
            Log.i(TAG, "发送拦截2");
            return false;
        }
        if (webSocketClient.isClosed()) {
            Log.i(TAG, "发送拦截3");
            return false;
        }
        if (!webSocketClient.isOpen()) {
            Log.i(TAG, "发送拦截4");
            return false;
        }
        return true;
    }

    public interface OnSocketUtilsCallBack {
        void onMessage(ByteBuffer byteBuffer);
    }
}
