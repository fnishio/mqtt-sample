package jp.gr.java_conf.fofn.sensorsample;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.json.JSONException;
import org.json.JSONObject;

public class MyMqttClient {
    private MqttAndroidClient mClient;
    private boolean mIsConnected = false;

    /**
     * Constructor
     */
    MyMqttClient(Context context) {
        mClient = new MqttAndroidClient(context, MyMqttConfig.ENDPOINT_URL, MyMqttConfig.CLIENT_ID);
    }

    /**
     *
     * @return true if connected.
     */
    public void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(MyMqttConfig.USER);
        options.setPassword(MyMqttConfig.PASSWORD.toCharArray());
        try {
            mClient.connect(options, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken mqttToken) {
                    Log.i(MyMqttConfig.TAG, "Client connected");
                    Log.i(MyMqttConfig.TAG, "Topics=" + mqttToken.getTopics());
                    mIsConnected = true;
                }

                @Override
                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    Log.i(MyMqttConfig.TAG, "Client connection failed: "+arg1.getMessage());
                    mIsConnected = false;
                }
            });
        } catch (MqttSecurityException e) {
            // do nothing.
        } catch (MqttException e) {
            // do nothing.
        }
    }

    public void close() {
        if (mClient != null) {
            mClient.close();
        }
    }

    public void publish(String event, String name, float value) {
        if (!mIsConnected) {
            return; // skip
        }

        /*
        Construct the publish event.
        Its format is:
          { "d" :
            {
              "<name>" : value
            }
          }
         */
        JSONObject json = new JSONObject();
        try {
            JSONObject json1 = new JSONObject();
            json1.put(name, value);
            json.put("d", json1);
        } catch (JSONException e) {
            // do nothing.
            return;
        }

        MqttMessage message = new MqttMessage();
        message.setPayload(json.toString().getBytes());
        try {
            mClient.publish(MyMqttConfig.getEventTopic(event), message);
        } catch (MqttException e) {
            // do nothing.
        }
    }
}
