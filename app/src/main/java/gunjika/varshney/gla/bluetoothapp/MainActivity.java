package gunjika.varshney.gla.bluetoothapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT=0;
    private static final int REQUEST_DISCOVER_BT=1;
    TextView status,paired;
    Button on,off,discover,pair;

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status=findViewById(R.id.statusblue);
        paired=findViewById(R.id.textView2);
        on=findViewById(R.id.button);
        off=findViewById(R.id.button2);
        discover=findViewById(R.id.button3);
        pair=findViewById(R.id.button4);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter==null)
        {
            status.setText("Bluetooth is not available");
        }
        else
        {
            status.setText("Bluetooth is available");
        }
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled())
                {
                    showToast("Turning On Bluetooth");
                    Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BT);
                }
                else
                {
                    showToast("Bluetooth is already on");
                }
            }
        });
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isDiscovering())
                {
                    showToast("making your device discoverable");
                    Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent,REQUEST_DISCOVER_BT);
                }
                else
                {
                    showToast("Bluetooth is already off");
                }

            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled())
                {
                    bluetoothAdapter.disable();
                    showToast("turning off bluetooth");
                }
            }
        });
        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled())
                {
                    paired.setText("paired devices");
                    Set<BluetoothDevice> devices=bluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device:devices)
                    {
                        paired.append("\nDevice"+device.getName()+","+device);
                    }
                }
                else
                {
                    showToast("turn on bluetooth to get paired devices");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode==RESULT_OK)
                {
                    showToast("Bluetooth is on");
                }
                else
                {
                    showToast("could't on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
}
