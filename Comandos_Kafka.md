# Comandos Kafka

- Iniciar zookeeper:  ```bin/zkServer.sh start```

- Iniciar kafka: 

  - Si se tiene Confluent instalado:  ```bin/confluent start kafka```  en este caso no es necesario encender antes Zookeeper
  
  - Si se tiene Apache Kafka:  ```bin/kafka-server-start ./etc/kafka/server.properties```
  
- Iniciar Confluent: ```./bin/confluent start```

- Iniciar KSQL:
  
  ```
  #LOG_DIR=./ksql_logs <path-to-confluent>/bin/ksql
  
  bin/ksql-server-start etc/ksql/ksql-server.properties
  
  bin/ksql http://localhost:8088
  ```
  
- Borrar todo:  ```rm -rf /tmp/kafka-logs /tmp/zookeeper```

- Crear topic: ```bin/kafka-topics --create --topic topic_name --zookeeper localhost:2181 --partitions 1 --replication-factor 1```

- Borrar topic:  ```bin/kafka-topics --zookeeper localhost:2181 --delete --topic topic_name```

- Listar topics: ```bin/kafka-topics --zookeeper localhost:2181 --list```

- Describir topic:  ```bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic topic_name```

- Conocer número de registros en topic:  ```bin/kafka-run-class.sh kafka.tools.GetOffsetShell  --broker-list <broker>:  <port>  --topic <topic-name> --time -1 --offsets 1  | awk -F  ":" '{sum += $3} END {print sum}'```

- Conocer número de mensajes por partición: ```bin/kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic topic_name --time -1```

### Producer

- Sin key

  ```bin/kafka-console-producer --broker-list localhost:9092 --topic topic_name```

- Con key y asignando el separados

  ```bin/kafka-console-producer --broker-list localhost:9092 --topic testing -property parse.key=true --property key.separator=,```

### Consumer

- Consumer desde el inicio, es decir, desde el primer dato insertado

  ```bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_name --from-beginning```

- Consumer desde el inicio, imprimiendo la key separada por **-**

  ```bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_name --from-beginning --property print.key=true --property key.separator="-"```

- Consumer desde el inicio solo los primeros x mensajes

  ```bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_name --from-beginning --max-messages 10```

- Consumer desde el inicio asignando el tipo de dato de la key y value

  ```bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_name --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer```

- Consumir desde un offset determinado: 

  ```bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_name -property print.key=true --property key.separator="-" --offset 517393 --partition 0```


- Listar los consumer group:  ```bin/kafka-consumer-groups  --list --bootstrap-server localhost:9092```

### Validación
 
```bin/kafka-verifiable-producer --topic first_topic --max-messages 200000 --broker-list localhost:9092```

```bin/kafka-verifiable-consumer --topic first_topic --max-messages 1000 --group-id testing --broker-list localhost:9092```


- Ids de brokers que tiene ZK:  ```bin/zookeeper-shell.sh localhost:2181 ls /brokers/ids```

- Estado Zookeeper:  ```bin/zkServer.sh status``` 
