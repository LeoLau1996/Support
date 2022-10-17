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

    public void openWebSocket(OnSocketUtilsCallBack callBack) {
        Log.e(TAG, "openWebSocket");
        webSocketServer = new WebSocketServer(new InetSocketAddress(9007)) {
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

    public void connect() {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://192.168.0.183:9007")) {
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
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.e(TAG, String.format("WebSocketClient ---- onClose    code = %s    reason = %s    remote = %s", code, reason, remote));
                }

                @Override
                public void onError(Exception ex) {
                    Log.e(TAG, String.format("WebSocketClient ---- onError    Exception = %s", ex.getMessage()));
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {

        }
    }

    public void send(byte[] bytes) {
        if (!canSend()) {
            Log.e(TAG, "发送拦截");
            return;
        }
        Log.e(TAG, String.format("发送数据    bytes = %s", bytes.length));
        webSocketClient.send(bytes);
    }

    public boolean canSend() {
        if (webSocketClient == null) {
            Log.e(TAG, "发送拦截1");
            return false;
        }
        if (webSocketClient.isClosing()) {
            Log.e(TAG, "发送拦截2");
            return false;
        }
        if (webSocketClient.isClosed()) {
            Log.e(TAG, "发送拦截3");
            return false;
        }
        if (!webSocketClient.isOpen()) {
            Log.e(TAG, "发送拦截4");
            return false;
        }
        return true;
    }

    public interface OnSocketUtilsCallBack {
        void onMessage(ByteBuffer byteBuffer);
    }
}
