package com.quimico.Back.service;

import com.quimico.Back.dto.ChemicalItemRequest;
import com.quimico.Back.dto.ChemicalItemResponse;
import com.quimico.Back.model.ChemicalItem;
import com.quimico.Back.repository.ChemicalItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ChemicalItemService {

    private final ChemicalItemRepository chemicalItemRepository;

    public ChemicalItemService(ChemicalItemRepository chemicalItemRepository) {
        this.chemicalItemRepository = chemicalItemRepository;
    }

    @Transactional
    public ChemicalItemResponse create(ChemicalItemRequest request) {
        chemicalItemRepository.findBySapNumber(request.sapNumber()).ifPresent(item -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SAP number already exists");
        });

        ChemicalItem item = ChemicalItem.builder()
                .name(request.name())
                .sapNumber(request.sapNumber())
                .expirationDate(request.expirationDate())
                .quantity(request.quantity())
                .location(request.location())
                .build();

        return toResponse(chemicalItemRepository.save(item));
    }

    @Transactional(readOnly = true)
    public List<ChemicalItemResponse> listAll() {
        return chemicalItemRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public ChemicalItemResponse update(Long id, ChemicalItemRequest request) {
        ChemicalItem item = chemicalItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        chemicalItemRepository.findBySapNumber(request.sapNumber())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "SAP number already exists");
                });

        item.setName(request.name());
        item.setSapNumber(request.sapNumber());
        item.setExpirationDate(request.expirationDate());
        item.setQuantity(request.quantity());
        item.setLocation(request.location());
        item.setNotifiedOneDayBefore(false);
        item.setNotifiedOnExpiration(false);

        return toResponse(chemicalItemRepository.save(item));
    }

    @Transactional
    public void delete(Long id) {
        if (!chemicalItemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        chemicalItemRepository.deleteById(id);
    }

    private ChemicalItemResponse toResponse(ChemicalItem item) {
        return new ChemicalItemResponse(
                item.getId(),
                item.getName(),
                item.getSapNumber(),
                item.getExpirationDate(),
                item.getQuantity(),
                item.getLocation(),
                item.isNotifiedOneDayBefore(),
                item.isNotifiedOnExpiration()
        );
    }
}
