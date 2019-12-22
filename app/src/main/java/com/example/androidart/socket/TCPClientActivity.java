package com.example.androidart.socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;
import com.example.androidart.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.androidart.TAGs.TAG_SOCKET;

public class TCPClientActivity extends BaseAppCompatActivity {

    private EditText editText;
    private TextView textView;

    ExecutorService executor = Executors.newCachedThreadPool();

    private PrintWriter pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textMessage = editText.getText().toString().trim();
                send(textMessage);
            }
        });

        Intent intent = new Intent(this, TCPServerService.class);
        intent.setAction("com.example.androidart.socket.connect_to_server");
        startService(intent);

        SystemClock.sleep(500); // TODO remove this
        executor.execute(new SocketRunnable());

    }

    @Override
    protected void onDestroy() {
        executor.shutdown();
        super.onDestroy();
    }

    void send(final String textMessage) {
        if (pw == null)
            return;
        if (TextUtils.isEmpty(textMessage))
            return;

        // 向服务端发消息
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pw.println(textMessage);

            }
        });
    }

    private class SocketRunnable implements Runnable {

        @Override
        public void run() {
            Log.d(TAG_SOCKET, "SocketRunnable$run: " + "threadName = " + Thread.currentThread().getName());
            Socket socket = null;
            BufferedReader br = null;
            try {
                // TODO 需要处理连接失败重试
                socket = new Socket("localhost", 8688);
                pw = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("connect server success");

                // 接收服务器端的消息
                br = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                while (!isFinishing()) {
                    String msg = br.readLine();
                    System.out.println("receive :" + msg);
                }
                System.out.println("quit...");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Utils.closeQuietly(pw, br, socket);
            }
        }
    }
}
