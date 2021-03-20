package com.raslib.rasdroid;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient extends AppCompatActivity {

    private Socket socket = null;
    private OutputStream outputStream = null;
    private String ip = null;
    private int port = 0;

    public SocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public SocketClient(String ip, String port) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    /**
     * Create new Socket and connect to Raspberry with ip address and port
     * @param status - TextView to change status
     */
    public void init(TextView status) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                outputStream = new DataOutputStream( socket.getOutputStream() );
                status.setText( (String)"You are connected to Raspberry");
            }
            catch (Exception e) {
                e.printStackTrace();
                status.setText( (String)"Can't connect to Raspberry");
            }
        }).start();
    }

    /**
     * Send signal to Raspberry
     * @param status - TextView to change status
     * @param message - Signal to send
     */
    public void send(TextView status, int message) {
        new Thread(() -> {
            if ( isConnected() ) {
                try {
                    outputStream.write(message);
                    if (status != null) {
                        status.setText( (String)("Signal sent :" + message) );
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Close socket connection
     */
    public void close() {
        new Thread(() -> {
            try {
                send(null, 0);
                socket.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Check if socket is connected to Raspberry
     * @return ? - return false on error
     */
    public boolean isConnected() {
        try {
            return socket.isConnected();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}