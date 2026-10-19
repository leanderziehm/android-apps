package com.leanderziehm.one;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity {

    private TextView outputText;
    private ScrollView scrollView;
    private int counter = 0;

    private static final String TAG = "SimpleServer";
    private static final int PORT = 8765;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button notifyButton = findViewById(R.id.notifyButton);

        outputText = findViewById(R.id.outputText);
        scrollView = findViewById(R.id.scrollView);

        notifyButton.setOnClickListener(v -> {
            counter++;
            String newLine = "Entry #" + counter + " added at " + System.currentTimeMillis();
            myLog(newLine);
            sendApiRequest(newLine);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createServer();
}
    private void myLog(String text){
        runOnUiThread(() -> {
            outputText.append("myLog:"+ text + "\n");
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
           
    }


    public static String getStackTraceAsString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    private void sendApiRequest(String text){

        myLog("call sendApiRequest");
        new Thread(() -> {
                try {
                    myLog("running in new Thread");
                    URL url = new URL("https://notify.leanderziehm.com/notify/me");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "*/*");
                    conn.setDoOutput(true);
                    // String jsonBody = "{\"text\":\""+ text +"\"}\"";
                    String jsonBody = "{\"text\":\"string\"}";
                    myLog("jsonbody:"+jsonBody);
                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(jsonBody.getBytes("UTF-8"));
                    }
                    int responseCode = conn.getResponseCode();
                    myLog(String.valueOf(responseCode));
                    conn.disconnect();
                } catch (Exception e) {
                    myLog(getStackTraceAsString(e));
                    // e.printStackTrace();
                }
        }).start();
    }
 

    private void createServer(){
          myLog("called createServer");
        // Start the server in a background thread
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                myLog("Server started on port " + PORT);
                while (isRunning) {
                    try (Socket client = serverSocket.accept()) {
                        // Read the request (optional for a simple hello world)
                        // Send the response
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "Content-Length: 11\r\n" +
                                "\r\n" +
                                "Hello World";
                        
                        OutputStream out = client.getOutputStream();
                        out.write(response.getBytes());
                        out.flush();
                    } catch (IOException e) {
                         myLog(getStackTraceAsString(e));
                        // Log.e(TAG, "Error handling client", e);
                    }
                }
            } catch (IOException e) {
                 myLog(getStackTraceAsString(e));
                // Log.e(TAG, "Server error", e);
            }
        }).start();
        ///
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false; // Stop the loop when activity is destroyed
    }

}