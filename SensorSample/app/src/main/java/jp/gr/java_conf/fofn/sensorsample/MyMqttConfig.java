package jp.gr.java_conf.fofn.sensorsample;

/* package */ class MyMqttConfig {
    public static final String TAG = "MQTT";

    private static final String ORG_ID = "<your org id>";
    private static final String DEVICE_ID = "<your device id>";
    private static final String DEVICE_TYPE = "<your device type>";
    private static final String EVENT_TOPIC_FORMAT = "iot-2/evt/%s/fmt/json";

    static final String USER = "use-token-auth";
    static final String PASSWORD = "<your password>";
    static final String ENDPOINT_URL = "tcp://" + ORG_ID + ".messaging.internetofthings.ibmcloud.com:1883";
    static final String CLIENT_ID = "d:" + ORG_ID +":" + DEVICE_TYPE + ":" + DEVICE_ID;

    static String getEventTopic(String eventId) {
        return String.format(EVENT_TOPIC_FORMAT, eventId);
    }
}
