package com.raslib.rasdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        buttonListener();
    }

    private void buttonListener() {
        onQuitBtn();
        onDirection();
    }

    /**
     * Close Activity, socket and return to main menu
     */
    private void quit() {
        ConnectionActivity.socketClient.close();
        startActivity( new Intent( getApplicationContext(), MainActivity.class ));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        quit();
    }

    private void onQuitBtn() {
        Button quitBtn = findViewById(R.id.quit_btn);
        quitBtn.setOnClickListener(v -> {
            quit();
        });
    }

    /**
     * Add event listener for button and select signal to send to Raspberry
     * @param button - button to add event listener
     * @param signal - signal to send for button
     */
    private void sendListener(Button button, int signal) {
        TextView statusTxt = findViewById(R.id.signal_txt);
        button.setOnClickListener(v -> {
            ConnectionActivity.socketClient.send(statusTxt, signal);
        });
    }

    /**
     * Forward signal : 2
     * Backward signal : 3
     * Left signal : 4
     * Right signal : 5
     */
    private void onDirection() {
        sendListener( findViewById(R.id.forward_btn), 2);
            sendListener( findViewById(R.id.left_btn), 4);
            sendListener( findViewById(R.id.right_btn), 5);
        sendListener( findViewById(R.id.backward_btn), 3);
    }
}