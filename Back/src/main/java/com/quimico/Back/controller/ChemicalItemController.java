package com.quimico.Back.controller;

import com.quimico.Back.dto.ChemicalItemRequest;
import com.quimico.Back.dto.ChemicalItemResponse;
import com.quimico.Back.service.ChemicalItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ChemicalItemController {

    private final ChemicalItemService chemicalItemService;

    public ChemicalItemController(ChemicalItemService chemicalItemService) {
        this.chemicalItemService = chemicalItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChemicalItemResponse create(@Valid @RequestBody ChemicalItemRequest request) {
        return chemicalItemService.create(request);
    }

    @GetMapping
    public List<ChemicalItemResponse> listAll() {
        return chemicalItemService.listAll();
    }

    @PutMapping("/{id}")
    public ChemicalItemResponse update(@PathVariable Long id, @Valid @RequestBody ChemicalItemRequest request) {
        return chemicalItemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        chemicalItemService.delete(id);
    }
}
