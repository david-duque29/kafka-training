package co.example.kafkatraining.handler;

import co.example.kafkatraining.jpa.entity.ItemEntity;
import co.example.kafkatraining.jpa.repository.ItemRepository;
import co.example.kafkatraining.schemas.Refund;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundHandler {

    private final ItemRepository itemRepository;

    public void manageRefund(Refund refund){
        // Verifica que el pedido existe y es elegible para reembolso
        Optional<ItemEntity> existentItem = itemRepository.findById(refund.itemId());
        if (existentItem.isEmpty()) {
            log.warn("Item not found for refund: {}", refund.itemId());
            return;
        }

        ItemEntity item = existentItem.get();

        //2. Actualiza el inventario
        item.incrementQuantity(refund.quantity());
        itemRepository.save(item);

        //3.Registrar el reembolso en el sistema
        log.info("Processed refund for item: {} with quantity: {}", item.getItemId(), refund.quantity());
    }
}
