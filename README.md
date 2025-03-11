
# 실시간 데이터 파이프라인 아키텍처
소스 코드 다운로드 https://github.com/bjpublic/streaming-data
를 돌아가게 만든 코드

- kafka_2.11-0.11.0.1
- apache-storm-1.0.2
- python 2.7.18
- java 1.8


<img width="1027" alt="image" src="https://github.com/user-attachments/assets/c6adc226-45ce-419e-82e6-67abec3c44c2" />
<img width="948" alt="image" src="https://github.com/user-attachments/assets/ffd084f2-ef27-4f5e-856a-9919e5ae6290" />
<img width="832" alt="image" src="https://github.com/user-attachments/assets/bfe62d8b-cc23-4a2a-8bee-f326190dfad7" />


토픽생성
```sh
#!/bin/bash

echo "create topic: meetup-raw-rsvps"
$KAFKA_HOME/bin/kafka-topic.sh --zookeeper localhost:2181 --create --topic meetup-raw-rsvps --partitions 1 --replication-factor 1

echo "create topic: meetup-topn-rsvps"
$KAFKA_HOME/bin/kafka-topic.sh --zookeeper localhost:2181 --create --topic meetup-topn-rsvps --partitions 1 --replication-factor 1
```

수집단계까지 실행 후
```sh
#!/bin/bash

echo "start zookeeper"
$KAFKA_HOME/bin/zookeeper-server-start.sh -daemon config/zookeeper.properties

echo "start kafka broker"
$KAFKA_HOME/bin/kafka-server-start.sh -daemon config/server.properties

echo "topic list"
$KAFKA_HOME/bin/kafka-topics.sh --zookeeper localhost:2181 --list

echo "start kafka-console-consumer, topic: meetup-raw-rsvps"
$KAFKA_HOME/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic meetup-raw-rsvps
```

분석단계까지 실행 후
```sh
#!/bin/bash

echo "top N data"
$KAFKA_HOME/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic meetup-topn-rsvps
```
