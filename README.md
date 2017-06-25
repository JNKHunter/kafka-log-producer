Kafka producer that simulates normal
and DDoS web traffic log messages.

#### Usage and command line arguments:
Build jar, then run:

java -jar ~/path_to_jar

####Command line arguments:

0. arg[0]: URL of a kafka boostrap server
1. arg[1]: kafka topic to publish to
2. arg[2]: Type of network traffic "normal" or "ddos"
3. arg[3]: Number of hosts generating messages 

#### example:

java -jar ~/path_to_jar.jar 0.0.0.0:9092 logs ddos 1000     