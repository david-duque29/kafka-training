package co.example.kafkatraining.producers;

import co.example.kafkatraining.schemas.Refund;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefundProducer {
    private static final String TOPIC_NAME= "REFUND";
    private final KafkaTemplate<String, Refund> kafkaTemplate;

    public void sendMessage(Refund refund){
        CompletableFuture<SendResult<String, Refund>> future = kafkaTemplate.send(TOPIC_NAME,refund);

        future.thenAccept(result ->  log.info("Refund sent successfully to topic {}: {}", TOPIC_NAME, refund))
                .exceptionally(ex ->{
                    log.error("Error sending refund message: {}", ex.getMessage());
                    return null;
                });
    }
}
