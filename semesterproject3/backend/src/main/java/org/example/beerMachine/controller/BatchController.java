package org.example.beerMachine.controller;

import org.example.beerMachine.service.BatchService;
import org.example.beerMachine.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/batch")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class BatchController {
    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public List<Batch> getBatches() {
        return batchService.getBatches();
    }

    @PostMapping
    public void makeNewBatch(@RequestBody Batch batch) {
        batchService.addNewBatch(batch);
    }

    @DeleteMapping(path = "{batchId}")
    public void deleteBatch(@PathVariable("batchId") Long batchId) {
        batchService.deleteBatch(batchId);
    }

    // Update batches in the database
    @PutMapping(path = "update/{batchId}")
    public void updateBatch(
            @PathVariable("batchId") Long batchId,
            @RequestParam(required = false) Integer recipe,
            @RequestParam(required = false) Integer quantity) {
        batchService.updateBatch(batchId, recipe, quantity);
    }

    @PutMapping(path = "updateStatus")
    public void updateBatchStatus(
            @RequestParam String status) {
        batchService.updateBatchStatus(status);
    }

    @PutMapping(path = "updateFinishTime")
    public void updateBatchFinishTime() {
        batchService.updateFinishTime();
    }

    // Endpoint to retrieve the latest batch
    @GetMapping("/latestBatch")
    public ResponseEntity<Optional<Batch>> getLatestBatch() {
        Optional<Batch> latestBatch = batchService.getLatestBatch();

        if (latestBatch != null) {
            return ResponseEntity.ok(latestBatch);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to retrieve the status of the latest batch
    @GetMapping("/latestStatus")
    public ResponseEntity<String> getLatestBatchStatus() {
        String latestBatchStatus = batchService.getLatestBatchStatus();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to retrieve the Id of the latest batch
    @GetMapping("/latestBatchId")
    public ResponseEntity<Long> getLatestBatchId() {
        Long latestBatchStatus = batchService.getLatestBatchId();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Endpoint to retrieve the Start Time of the latest batch
    @GetMapping("/latestBatchStartTime")
    public ResponseEntity<LocalDateTime> getLatestBatchStartTime() {
        LocalDateTime latestBatchStatus = batchService.getLatestBatchStartTime();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{batchId}")
    public Optional<Batch> getBatchById(@PathVariable Long batchId) {
        return batchService.getBatchById(batchId);
    }



}