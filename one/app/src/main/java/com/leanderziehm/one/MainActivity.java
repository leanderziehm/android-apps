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

public class MainActivity extends AppCompatActivity {

    private TextView outputText;
    private ScrollView scrollView;
    private int counter = 0;

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
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

}
    private void myLog(String text){
            outputText.append("myLog:"+ text + "\n");
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }

        // notifyButton.setOnClickListener(v -> {
        //     new Thread(() -> {
        //         try {
        //             URL url = new URL("https://notify.leanderziehm.com/notify/me");
        //             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //             conn.setRequestMethod("POST");
        //             conn.setRequestProperty("Content-Type", "application/json");
        //             conn.setRequestProperty("Accept", "*/*");
        //             conn.setDoOutput(true);

        //             String jsonBody = "{\"text\":\"string\"}";
        //             try (OutputStream os = conn.getOutputStream()) {
        //                 os.write(jsonBody.getBytes("UTF-8"));
        //             }

        //             int responseCode = conn.getResponseCode();
        //             conn.disconnect();
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }).start();
        // });


}