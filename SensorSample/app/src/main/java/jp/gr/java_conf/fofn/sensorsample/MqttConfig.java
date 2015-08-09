package jp.gr.java_conf.fofn.sensorsample;

/**
 * Created by nishio on 2015/08/08.
 */
/* package */ class MqttConfig {
    private static final String ORG_ID = "lplwal";
    private static final String DEVICE_ID = "aabbccddee12";
    private static final String DEVICE_TYPE = "MQTTDevice";
    private static final String EVENT_TOPIC_FORMAT = "iot-2/evt/{event_id}/fmt/json";

    static final String PASSWORD = "0j2QnF6llDQeB!G2go";
    static final String USER = "use-token-auth";
    static final String ENDPOINT_URL = ORG_ID + ".messaging.internetofthings.ibmcloud.com";
    static final String CLIENT_ID = "d:" + ORG_ID +":" + DEVICE_TYPE + ":" + DEVICE_ID;
}
