package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER")); // O consumer escolhe o tópico para ouvir
            // É possível escutar de vários tópicos, mas não é normal
        while (true) { // Rodar continuamentee para ouvir as mensagens
            var records = consumer.poll(Duration.ofMillis(100)); // perguntar se há mensagem a ser consumida por determinado tempo, caso contrário, retorna mensagem vazia
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    // Verificando se os pedidos são fraudes
                    System.out.println("__________________________________________");
                    System.out.println("Processsando new order, checking for fraud");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());
                    try {
                        Thread.sleep(5000); // simula processo demorado para detectar fraude
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Order processed");
                }
            }

        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // serviços com grupos diferentes recebm todas as mensagens
        // serviços com o mesmo grupo dividem as mensagens entre si
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, FraudDetectorService.class.getSimpleName() + "_" + UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "1"); // De um em um , o kafka vai commitanndo
        return properties;
    }
}
