package org.example.beerMachine.service;

import org.example.beerMachine.model.Batch;
import org.example.beerMachine.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BatchService {

    private final BatchRepository batchRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public List<Batch> getBatches() {
        return batchRepository.findAll();
    }

    public void addNewBatch(Batch batch) {
        batchRepository.save(batch);
    }

    public void deleteBatch(Long batchId) {
        boolean exists = batchRepository.existsById(batchId);
        if (!exists) {
            throw new IllegalStateException("No batch found with id: " + batchId);
        }
        batchRepository.deleteById(batchId);
    }

    @Transactional
    public void updateBatch(Long batchId, Integer recipe, Integer quantity) {
        Batch batch = batchRepository.findById(batchId).orElseThrow(() -> new IllegalStateException(
                "No batch found with id: " + batchId));
        if (recipe != null && !Objects.equals(batch.getRecipe(), recipe)) {
            batch.setRecipe(recipe);
        }
        if (quantity != null && !Objects.equals(batch.getQuantity(), quantity)) {
            batch.setQuantity(quantity);
        }
    }

    @Transactional
    public void updateBatchStatus(String status) {
        // Find the latest batch in the database
        Optional<Batch> latestBatchOptional = batchRepository.findFirstByOrderByStartTimeDesc();

        if (latestBatchOptional.isPresent()) {
            // If a latest batch exists, update its values
            Batch latestBatch = latestBatchOptional.get();
            if (!Objects.equals(latestBatch.getStatus(), status)) {
                latestBatch.setStatus(status);
            }
            // Save the updated batch entity
            batchRepository.save(latestBatch);
        } else {
            // If no batch exists, do nothing or handle as needed
            System.out.println("No existing batch found. Values not updated.");
        }
    }

    @Transactional
    public void updateFinishTime() {
        // Find the latest batch in the database
        Optional<Batch> latestBatchOptional = batchRepository.findFirstByOrderByStartTimeDesc();

        if (latestBatchOptional.isPresent()) {
            // If a latest batch exists, update its values
            Batch latestBatch = latestBatchOptional.get();

            if (Objects.equals(latestBatch.getFinishTime(), null)) {
                latestBatch.setFinishTime(LocalDateTime.now());
            }
            // Save the updated batch entity
            batchRepository.save(latestBatch);
        } else {
            // If no batch exists, do nothing or handle as needed
            System.out.println("No existing batch found. Values not updated.");
        }
    }

    // Fetch the status of the latest batch
    public String getLatestBatchStatus() {
        Optional<Batch> latestBatchOptional = batchRepository.findFirstByOrderByStartTimeDesc();
        return latestBatchOptional.map(Batch::getStatus).orElse(null);
    }

    // Fetch the id of the latest batch
    public Long getLatestBatchId() {
        Optional<Batch> latestBatchOptional = batchRepository.findFirstByOrderByStartTimeDesc();
        return latestBatchOptional.map(Batch::getId).orElse(null);
    }
}