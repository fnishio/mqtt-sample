### MQTT client
###
### 1. Send incremental counter event periodecally.
### 2. When receiving reset command, reset counter to the "count" value.

import paho.mqtt.client as mqtt
import time
import json

# Definitions
import device_conf as c
CLIENT_ID_FORMAT = "d:{org_id}:{type_id}:{device_id}"
DEVICE_TYPE = "MQTTDevice"
USERNAME = "use-token-auth"
ENDPOINT_FORMAT = "{org_id}.messaging.internetofthings.ibmcloud.com"
EVENT_TOPIC_FORMAT = "iot-2/evt/{event_id}/fmt/json"
COMMAND_TOPIC_FORMAT = "iot-2/cmd/{command_id}/fmt/json"

def on_connect(client, userdata, flags, rc):
#    print("Connected with result code "+str(rc))
    client.subscribe(COMMAND_TOPIC_FORMAT.format(command_id="cid"))

def on_message(client, userdata, msg):
    global count
    print(msg.topic+" "+str(msg.payload))
    payload = json.loads(msg.payload.decode())
    if payload['cmd'] == "reset":
        count = payload['count']

client_id = CLIENT_ID_FORMAT.format(
    org_id=c.ORG_ID, type_id=DEVICE_TYPE, device_id=c.DEVICE_ID)
endpoint = ENDPOINT_FORMAT.format(org_id=c.ORG_ID)

client = mqtt.Client(client_id)
client.username_pw_set(USERNAME, c.PASSWORD)
client.on_connect = on_connect
client.on_message = on_message
client.connect(endpoint, 1883)

count = 0
while client.loop() == 0:
    msg = json.dumps({ "d" : { "count" : count } });
    client.publish(EVENT_TOPIC_FORMAT.format(event_id="eid"),
        msg, 0, True)
    print("sent: " + msg)
    time.sleep(1.5)
    count = count + 1
