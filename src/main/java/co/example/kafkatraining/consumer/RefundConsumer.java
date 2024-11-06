package co.example.kafkatraining.consumer;

import co.example.kafkatraining.handler.RefundHandler;
import co.example.kafkatraining.schemas.Refund;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefundConsumer {
    private final RefundHandler refundHandler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "REFUND", groupId = "refund-group")
    public void recieveRefundMessage(String refundJson){
        try {
            Refund refund = objectMapper.readValue(refundJson, Refund.class);
            log.info("Received refund message: {}", refund);
            refundHandler.manageRefund(refund);
        } catch (JsonProcessingException e) {
            log.error("Error parsing refund message", e);
        }
    }
}

