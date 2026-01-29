package pe.com.yape.antifraud.kafka.events;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class TransactionCreatedEvent {

    private UUID transactionExternalId;
    private BigDecimal value;

}