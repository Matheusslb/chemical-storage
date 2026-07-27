package com.quimico.Back.service;

import com.quimico.Back.dto.DeviceRegistrationRequest;
import com.quimico.Back.model.UserDevice;
import com.quimico.Back.repository.UserDeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public DeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    @Transactional
    public void registerDevice(DeviceRegistrationRequest request) {
        userDeviceRepository.findByDeviceToken(request.deviceToken())
                .ifPresentOrElse(existing -> {
                    existing.setGoogleUserId(request.googleUserId());
                    userDeviceRepository.save(existing);
                }, () -> userDeviceRepository.save(
                        UserDevice.builder()
                                .googleUserId(request.googleUserId())
                                .deviceToken(request.deviceToken())
                                .build()
                ));
    }
}
