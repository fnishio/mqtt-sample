package jp.gr.java_conf.fofn.sensorsample;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SensorActivity extends ActionBarActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mLight;
    private MyMqttClient mClient;
    private TextView mText;
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mText = (TextView)findViewById(R.id.text);

        // dump sensor list
        /*
        mText.setText("");
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors) {
            mText.append(s.getName() + ':' + s.getType() + '\n');
        }
        */

        // MQTT client
        mClient = new MyMqttClient(this);
        mClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (mCount++ < 10) {
            return;
        } else {
            mCount = 0; // reset counter
        }

        float value = event.values[0];

        String lightLevel = String.valueOf(value);
        mText.setText("light: " + String.valueOf(value));

        mClient.publish("sensor", "light", value);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing.
    }
}
