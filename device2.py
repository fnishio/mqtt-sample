### MQTT client 2
###
### 1. subscirbe LED toggle command
### 2. In receiving toggle command, toggle LED.

import paho.mqtt.client as mqtt
import RPi.GPIO as GPIO
import time
import json

# Definitions
import device_conf as c
CLIENT_ID_FORMAT = "d:{org_id}:{type_id}:{device_id}"
DEVICE_TYPE = "MQTTDevice"
USERNAME = "use-token-auth"
ENDPOINT_FORMAT = "{org_id}.messaging.internetofthings.ibmcloud.com"
COMMAND_TOPIC_FORMAT = "iot-2/cmd/{command_id}/fmt/json"
LED_PIN = 25
on_off = False

def gpio_init():
    global on_off
    GPIO.setmode(GPIO.BCM)
    GPIO.setup (LED_PIN, GPIO.OUT)
    GPIO.output(LED_PIN, on_off)

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe(COMMAND_TOPIC_FORMAT.format(command_id="led"))

def on_message(client, userdata, msg):
    payload = json.loads(msg.payload.decode())
    if payload['cmd'] == "toggle":
       print(msg.topic+" "+str(msg.payload))
       # control GPIO
       global on_off
       on_off = ~on_off
       GPIO.output(LED_PIN, on_off)

gpio_init()

client_id = CLIENT_ID_FORMAT.format(
    org_id=c.ORG_ID, type_id=DEVICE_TYPE, device_id=c.DEVICE_ID)
endpoint = ENDPOINT_FORMAT.format(org_id=c.ORG_ID)

client = mqtt.Client(client_id)
client.username_pw_set(USERNAME, c.PASSWORD)
client.on_connect = on_connect
client.on_message = on_message
client.connect(endpoint, 1883)

client.loop_forever()
