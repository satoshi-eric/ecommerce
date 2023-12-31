package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL")); // O consumer escolhe o tópico para ouvir
            // É possível escutar de vários tópicos, mas não é normal
        while (true) { // Rodar continuamentee para ouvir as mensagens
            var records = consumer.poll(Duration.ofMillis(100)); // perguntar se há mensagem a ser consumida por determinado tempo, caso contrário, retorna mensagem vazia
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    // Verificando se os pedidos são fraudes
                    System.out.println("__________________________________________");
                    System.out.println("Send email");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());
                    try {
                        Thread.sleep(1000); // simula processo demorado para detectar fraude
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Email sent");
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
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());
        return properties;
    }
}
