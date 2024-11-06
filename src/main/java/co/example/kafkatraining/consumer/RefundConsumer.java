package co.example.kafkatraining.consumer;

import co.example.kafkatraining.handler.RefundHandler;
import co.example.kafkatraining.schemas.Refund;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefundConsumer {
    private final RefundHandler refundHandler;

    @KafkaListener(topics ="REFUND", groupId = "refund-group")
    public void recieveRefundMessage(Refund refund){
        log.info("Received refund message: {}", refund);
        refundHandler.manageRefund(refund);
    }

}
