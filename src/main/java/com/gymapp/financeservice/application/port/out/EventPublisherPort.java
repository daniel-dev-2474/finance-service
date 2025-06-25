package com.gymapp.financeservice.application.port.out;

import com.gymapp.financeservice.infrastructure.event.PurchaseCreatedEvent;

public interface EventPublisherPort {

  void emitPurchaseCreated(PurchaseCreatedEvent event);
}
