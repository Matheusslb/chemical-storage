package com.quimico.Back.repository;

import com.quimico.Back.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    Optional<UserDevice> findByDeviceToken(String deviceToken);

    List<UserDevice> findAllByGoogleUserId(String googleUserId);
}
