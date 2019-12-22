package com.example.androidart.socket;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import static com.example.androidart.TAGs.TAG_SOCKET;

public class TCPServerService extends IntentService {

    private boolean mIsServiceDestroyed;

    public TCPServerService() {
        super("tcp-server-intent-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    @WorkerThread
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG_SOCKET, "onHandleIntent: " + "threadName = " + Thread.currentThread().getName());
        if ("com.example.androidart.socket.connect_to_server".equals(intent.getAction())) {
            try {
                ServerSocket serverSocket = new ServerSocket(8688);

                while (!mIsServiceDestroyed) {
                    try {
                        // 接受客户端请求
                        final Socket client = serverSocket.accept();
                        System.out.println("accept");
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    responseClient(client);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            };
                        }.start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @WorkerThread
    void responseClient(Socket client) throws IOException {
        // 用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(
                client.getInputStream()));
        // 用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎来到聊天室！");
        while (!mIsServiceDestroyed) {
            String str = in.readLine();
            System.out.println("msg from client:" + str);
            // 这里表示客户端已断开
            if (str == null) {
                break;
            }
            int i = new Random().nextInt(10);
            String msg = "服务端回复消息: " + i;
            out.println(msg);
            System.out.println("send :" + msg);
        }
        System.out.println("client quit.");
    }
}
