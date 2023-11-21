package org.example.beerMachine.service;

import org.example.beerMachine.model.Batch;
import org.example.beerMachine.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updateStudent(Long batchId, Integer recipe, Integer quantity, Integer machineSpeed) {
        Batch batch = batchRepository.findById(batchId).orElseThrow(() -> new IllegalStateException(
                "No batch found with id: " + batchId));
        if(recipe != null && !Objects.equals(batch.getRecipe(), recipe)){
            batch.setRecipe(recipe);
        }
        if(quantity != null && !Objects.equals(batch.getQuantity(), quantity)){
            batch.setQuantity(quantity);
        }
        if(machineSpeed != null && !Objects.equals(batch.getMachineSpeed(), machineSpeed)){
            batch.setMachineSpeed(machineSpeed);
        }
    }
}
