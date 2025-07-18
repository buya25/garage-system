package com.garage.service;

import com.garage.entity.Part;
import com.garage.repository.PartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {

    private final PartRepository repo;

    public PartService(PartRepository repo) {
        this.repo = repo;
    }

    public Part create(Part part) {
        return repo.save(part);
    }

    public List<Part> list() {
        return repo.findAll();
    }

    public Part update(Long id, Part part) {
        Part existing = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Part not found: " + id));
        existing.setName(part.getName());
        existing.setPartNumber(part.getPartNumber());
        existing.setStockLevel(part.getStockLevel());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public boolean isAvailable(String partNumber, int qty) {
        Part part = repo.findByPartNumber(partNumber)
            .orElseThrow(() -> new RuntimeException("Part not found: " + partNumber));
        return part.getStockLevel() >= qty;
    }

    public void consume(String partNumber, int qty) {
        Part part = repo.findByPartNumber(partNumber)
            .orElseThrow(() -> new RuntimeException("Part not found: " + partNumber));
        if (part.getStockLevel() < qty) {
            throw new RuntimeException("Insufficient stock for part: " + partNumber);
        }
        part.setStockLevel(part.getStockLevel() - qty);
        repo.save(part);
    }

    public Part restock(String partNumber, int qty) {
        Part part = repo.findByPartNumber(partNumber)
            .orElseThrow(() -> new RuntimeException("Part not found: " + partNumber));
        part.setStockLevel(part.getStockLevel() + qty);
        return repo.save(part);
    }
}