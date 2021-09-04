# Primeros ejercicios Kafka

1. Encender Zookeeper

   ```bin/zkServer.sh start```

2. Encender Kafka

   ```bin/kafka-server.start /etc/kafka/server.properties```

3. Revisar status Zookeeper (no es necesario realizarlo siempre que se encienda el ZK)

   ```bin/zkServer.sh status```

4. Listar tópicos

   ```bin/kafka-topics --zookeeper localhost:2181 --list```

5. Crear un tópico de prueba

   ```bin/kafka-topics.sh --create --topic topic_name --zookeeper localhost:2181 --partitions 1 --replication-factor 1```

6. Listar tópicos para validar que se creó 

7. Con un producer enviar 10 mensajes al tópico creado

   NOTA para terminar un producer o consumer desde consola solo se le da CTRL + C
   
   ```bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topic_name```

8. Consumir el tópico creado señalando que sea desde el inicio (beginning)

   ```bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_name --from-beginning```

9. Describir el tópico

   ```bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic topic_name```

10. Borrar el tópico

    ```bin/kafka-topics --zookeeper localhost:2181 --delete --topic topic-eritrocito```
