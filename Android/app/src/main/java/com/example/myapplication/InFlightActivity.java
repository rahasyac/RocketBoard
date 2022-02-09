package com.example.myapplication;


import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Bluetooth dependencies
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Set;
import java.util.Date;
import java.util.Calendar;
import java.util.UUID;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class InFlightActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter fAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> dataTypes = new ArrayList<>();
    private ArrayList<String> fData = new ArrayList<>();
    // Debugging
    private static final String TAG = "BluetoothChatService";

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    private boolean connected = false;
    FileOutputStream outStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_flight);
        getData();
        Button btButton = (Button)findViewById(R.id.BlueToothbtn);
        btButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    findBT();
                    openBT();
                }
                catch (IOException ex) { }
            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        File file = new File(this.getFilesDir(), "Flight: " + currentTime.toString());

        try{
            outStream = openFileOutput(file.getName(), this.MODE_PRIVATE);

        } catch (Exception e){
            e.printStackTrace();
        }


        Thread updateText = new Thread() {
            @Override
            public void run() {
                try {
                    while(!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateData();
                            }
                        });
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        updateText.start();
    }

    void findBT()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            Log.d("BT","No bluetooth adapter available");
        }

        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                Log.d("BT", "Device List: " + device.getName());
                if(device.getName().equals("RNBT-8BE8"))
                {
                    mmDevice = device;
                    Log.d("BT","Bluetooth Device Found " + device.getName());
                    break;
                }
            }
        }else{
            Log.d("BT","No bluetooth devices...");
        }

    }

    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        Log.d("BT","Bluetooth Opened");
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        beginListenForData();

    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            Log.d("BT","Bytes available " + bytesAvailable);
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");


                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            Log.i("BTData", data);
                                            writeToFile(data);

                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }



    private void getData() {
        dataTypes.add("Alt:");
        fData.add("7");

        dataTypes.add("Vel:");
        fData.add("7");

        dataTypes.add("Thrust:");
        fData.add("7");

        dataTypes.add("Swag:");
        fData.add("7");

        initRecyclerView();
    }

    private void writeToFile(String data){
        try{
            outStream.write(data.getBytes());
            outStream.write("\n".getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.in_flight_recycler);
        inFlightAdapter adapter = new inFlightAdapter(dataTypes, fData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateData() {
        dataTypes.clear();
        fData.clear();


        dataTypes.add("Alt:");
        fData.add("8");

        dataTypes.add("Vel:");
        fData.add("8");

        dataTypes.add("Thrust:");
        fData.add("8");

        dataTypes.add("Swag:");
        fData.add("8");

        RecyclerView recyclerView = findViewById(R.id.in_flight_recycler);
        inFlightAdapter adapter = new inFlightAdapter(dataTypes, fData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    //go to map
    public void goToMaps(View view) {
        startActivity(new Intent(this, MapsActivity.class));
    }

}
