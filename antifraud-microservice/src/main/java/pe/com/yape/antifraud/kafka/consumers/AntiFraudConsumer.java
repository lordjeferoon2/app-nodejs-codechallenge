package pe.com.yape.antifraud.kafka.consumers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pe.com.yape.antifraud.kafka.events.TransactionCreatedEvent;
import pe.com.yape.antifraud.kafka.events.TransactionUpdatedEvent;
import pe.com.yape.antifraud.kafka.producers.AntiFraudProducer;
import pe.com.yape.antifraud.models.AntiFraudDecision;
import pe.com.yape.antifraud.services.AntiFraudService;

@Component
@RequiredArgsConstructor
public class AntiFraudConsumer {

    private final AntiFraudService antifraudService;
    private final AntiFraudProducer producer;

    @KafkaListener(
            topics = "${app.kafka.topics.transaction-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(TransactionCreatedEvent event) {

        AntiFraudDecision decision = antifraudService.evaluate(event);

        producer.sendTransactionUpdated(
                event.getTransactionExternalId(),
                decision.isApproved()
                        ? "APPROVED"
                        : "REJECTED"
        );
    }
}