package com.quimico.Back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(LogNotificationService.class);

    @Override
    public void notifyAllDevices(String title, String body) {
        logger.info("Notification mock | title: {} | body: {}", title, body);
    }
}
