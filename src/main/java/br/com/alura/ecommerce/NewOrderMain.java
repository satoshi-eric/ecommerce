package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        for (var i = 0; i < 100; i++) {
            var key = UUID.randomUUID().toString(); // a partiação que vai receber a mensagem é determinada pela sua chave
            var value = key + ",321312,231312"; // ID do pedido, ID do usuário e valor da compra
            var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", key, value); // Criando mensagem
            Callback callback = (data, ex) -> { // Producer manda mensagem para o broker
                // Callback para mostrar logs no terminal
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }
                System.out.println("Sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
            };
            var email = "Thanks for your order! We are processing your order";
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key, email);
            producer.send(record, callback).get();  // O método send é asíncrono, para isso, usamos o get
            producer.send(emailRecord, callback).get();
        }
    }

    private static Properties properties() {
        // Propriedades para conectar o produtor no broker do kafka
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
