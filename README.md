MQTT Sample
============
This sample demonstrates how to connect IBM Internet of Things Foundation
MQTT broker with Python and Android (Java).

device.py
---------
Pre-requisites are python and
[paho-mqtt](https://pypi.python.org/pypi/paho-mqtt) library.
Publish the incremental counter value, and subscribe the command to reset the counter.

device2.py
----------
This code is for rasbperri pi, python and
[paho-mqtt](https://pypi.python.org/pypi/paho-mqtt).
Subscribe the command, and toggle LED.

SensorSample
------------
Android Studio project using [Paho](http://www.eclipse.org/paho/).
Publish the light sensor value to MQTT broker.

Details
-------
See [the documents]() for more detail (in Japanese).
