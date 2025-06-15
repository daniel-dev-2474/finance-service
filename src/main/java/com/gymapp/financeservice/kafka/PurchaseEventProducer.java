package com.gymapp.financeservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.financeservice.event.PurchaseCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseEventProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  private static final String PURCHASE_TOPIC = "purchase.created";

  public void emitPurchaseCreated(PurchaseCreatedEvent event) {
    try {
      String json = objectMapper.writeValueAsString(event);
      kafkaTemplate.send(PURCHASE_TOPIC, json);
      log.info("[purchase-service] Emitted purchase.created event: {}", json);
    } catch (JsonProcessingException e) {
      log.error("[purchase-service] Failed to serialize PurchaseCreatedEvent", e);
    }
  }
}