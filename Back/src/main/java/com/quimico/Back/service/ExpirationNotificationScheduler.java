package com.quimico.Back.service;

import com.quimico.Back.model.ChemicalItem;
import com.quimico.Back.repository.ChemicalItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpirationNotificationScheduler {

    private final ChemicalItemRepository chemicalItemRepository;
    private final NotificationService notificationService;

    public ExpirationNotificationScheduler(ChemicalItemRepository chemicalItemRepository,
                                           NotificationService notificationService) {
        this.chemicalItemRepository = chemicalItemRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void notifyOneDayBeforeExpiration() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<ChemicalItem> items = chemicalItemRepository
                .findAllByExpirationDateAndNotifiedOneDayBeforeFalse(tomorrow);

        for (ChemicalItem item : items) {
            notificationService.notifyAllDevices(
                    "Produto perto do vencimento",
                    "Item " + item.getName() + " (SAP " + item.getSapNumber() + ") vence amanhã."
            );
            item.setNotifiedOneDayBefore(true);
            chemicalItemRepository.save(item);
        }
    }

    @Scheduled(cron = "0 5 8 * * *")
    @Transactional
    public void notifyOnExpirationDate() {
        LocalDate today = LocalDate.now();
        List<ChemicalItem> items = chemicalItemRepository
                .findAllByExpirationDateAndNotifiedOnExpirationFalse(today);

        for (ChemicalItem item : items) {
            notificationService.notifyAllDevices(
                    "Produto vencendo hoje",
                    "Item " + item.getName() + " (SAP " + item.getSapNumber() + ") vence hoje."
            );
            item.setNotifiedOnExpiration(true);
            chemicalItemRepository.save(item);
        }
    }
}
