package com.raslib.rasdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectionActivity extends AppCompatActivity {

    private static String dbIpAddress = "dbIpAddress.rasdroid";
    private static String dbPort = "dbPort.rasdroid";

    private EditText ipAddrEdt = null;
    private EditText portEdt = null;

    public static SocketClient socketClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ipAddrEdt = findViewById(R.id.ip_addr_edt);
        portEdt = findViewById(R.id.port_edt);

        init();
        onConnectBtn();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (socketClient != null) {
            socketClient.close();
        }
        finish();
    }

    /**
     * Save ip address and port of Raspberry PI
     * @param ip - ip address of Raspberry to save
     * @param port - port of Raspberry to save
     */
    private void saveData(String ip, String port) {
        DataManager.saveData( dbIpAddress, ip, getApplicationContext() );
        DataManager.saveData( dbPort, port, getApplicationContext() );
    }

    /**
     * Get ip address and port of Raspberry PI
     * @return new String[] - ip address and port
     */
    private String[] getData() {
        return new String[] {
            (String)DataManager.getData( dbIpAddress, getApplicationContext() ),
            (String)DataManager.getData( dbPort, getApplicationContext() )
        };
    }

    /**
     * Get data from DataManager and set text to two EditText
     * If data retrieved == null, don't set text
     */
    private void init() {
        ipAddrEdt.setText( (String)"Ip address");
        portEdt.setText( (String)"Port");

        String[] data = getData();

        if (data[0] != null || data[1] != null) {
            ipAddrEdt.setText( data[0] );
            portEdt.setText( data[1] );
        }
    }

    /**
     * Get content of EditText
     * @return new String[] - retrieved content of two EditText
     */
    private String[] getContent() {
        return new String[] {
            ipAddrEdt.getText().toString(),
            portEdt.getText().toString()
        };
    }

    /**
     * Check if a string is a number
     * Values:
     * '0' -> 48, '9' -> 57
     * @param text - String to check
     * @return ? - return false if it's not a number, else return true
     */
    private boolean isNumber(String text) {
        for (int i = 0; i < text.length(); i++) {
            if ( text.charAt(i) < 48 || text.charAt(i) > 57 ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Create new socket client to connect to Raspberry
     * @param ip - ip of Raspberry
     * @param port - port of Raspberry server socket
     */
    private void connect(String ip, String port) {
        saveData(ip, port);
        changeStatus("Connection to Raspberry ...");

        String[] data = getData();

        if ( isNumber( data[1] ) && !data[0].isEmpty() ) {
            socketClient = new SocketClient( data[0], data[1] );
            socketClient.init( findViewById(R.id.status_txt) );

            // Check if socket is connected to Raspberry
            new Thread(() -> {
                if (socketClient != null) {
                    while ( !socketClient.isConnected() );
                    startActivity( new Intent( getApplicationContext(), ControlActivity.class ));
                }
            }).start();
        }
        else {
            changeStatus("Invalid information, can't connect to Raspberry");
        }
    }

    /**
     * When it's clicked, create new socket ( connect() method )
     */
    private void onConnectBtn() {
        Button connectBtn = findViewById(R.id.connect_btn);
        connectBtn.setOnClickListener(v -> {
            String[] content = getContent();
            connect( content[0], content[1] );
        });
    }

    /**
     * Change text at bottom
     * @param status - text to set
     */
    private void changeStatus(String status) {
        TextView statusText = findViewById(R.id.status_txt);
        statusText.setText(status);
    }
}