package pe.com.yape.antifraud.kafka.producers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pe.com.yape.antifraud.kafka.events.TransactionUpdatedEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AntiFraudProducer {

    @Value("${app.kafka.topics.transaction-updated}")
    private String topic;

    private final KafkaTemplate<String, TransactionUpdatedEvent> kafkaTemplate;

    public void sendTransactionUpdated(UUID transactionExternalId, String status) {

        TransactionUpdatedEvent event = TransactionUpdatedEvent.builder()
                .transactionExternalId(transactionExternalId)
                .status(status)
                .build();

        kafkaTemplate.send(topic, event);
    }

}