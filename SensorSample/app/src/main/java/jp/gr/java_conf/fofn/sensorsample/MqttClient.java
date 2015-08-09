package jp.gr.java_conf.fofn.sensorsample;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by nishio on 2015/08/08.
 */
public class MqttClient {
    private MqttAndroidClient mClient;

    /**
     * Constructor
     */
    MqttClient(Context context) {
        mClient = new MqttAndroidClient(context, MqttConfig.ENDPOINT_URL, MqttConfig.CLIENT_ID);
    }

    /**
     *
     * @return true if connected.
     */
    public boolean connect() {
        boolean isConnected = true;
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(MqttConfig.USER);
        options.setPassword(MqttConfig.PASSWORD.toCharArray());
        try {
            mClient.connect(options);
        } catch (MqttException e) {
            isConnected = false;
        }
        return isConnected;
    }

    public void publish(String event, String value) {

    }
}
