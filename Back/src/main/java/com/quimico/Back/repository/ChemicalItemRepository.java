package com.quimico.Back.repository;

import com.quimico.Back.model.ChemicalItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChemicalItemRepository extends JpaRepository<ChemicalItem, Long> {

    Optional<ChemicalItem> findBySapNumber(String sapNumber);

    List<ChemicalItem> findAllByExpirationDateAndNotifiedOneDayBeforeFalse(LocalDate expirationDate);

    List<ChemicalItem> findAllByExpirationDateAndNotifiedOnExpirationFalse(LocalDate expirationDate);
}
