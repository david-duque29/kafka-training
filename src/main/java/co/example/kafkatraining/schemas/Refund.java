package co.example.kafkatraining.schemas;

import lombok.Builder;

@Builder
public record Refund(String id, String itemId, int quantity, double amount) {
}
