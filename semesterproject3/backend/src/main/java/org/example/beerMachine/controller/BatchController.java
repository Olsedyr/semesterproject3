package org.example.beerMachine.controller;

import org.example.beerMachine.repository.BatchRepository;
import org.example.beerMachine.service.BatchService;
import org.example.beerMachine.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/batch")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class BatchController {
    private final BatchService batchService;
    private final List<Batch> batchQueue = new ArrayList<>();

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/batchQueue")
    public ResponseEntity<List<Batch>> getBatchQueue() {
        return ResponseEntity.ok(batchQueue);
    }


    @PostMapping("/addBatchToQueue")
    public ResponseEntity<String> addToBatchQueue(@RequestBody Batch batch) {
        batchQueue.add(batch);
        System.out.printf(batchQueue.toString());
        return ResponseEntity.ok("Batch added to the queue.");
    }

    @DeleteMapping("/removeFromQueue/{index}")
    public ResponseEntity<String> removeFromQueue(@PathVariable int index) {
        if (index >= 0 && index < batchQueue.size()) {
            batchQueue.remove(index);
            System.out.printf(batchQueue.toString());
            return ResponseEntity.ok("Batch removed from the queue.");
        } else {
            return ResponseEntity.badRequest().body("Invalid index.");
        }
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

    @GetMapping("/latestBatchFinishTime")
    public ResponseEntity<LocalDateTime> getLatestBatchFinishTime() {
        LocalDateTime latestBatchStatus = batchService.getLatestBatchFinishTime();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latestBatchTimeDifference")
    public ResponseEntity<Long> getLatestBatchTimeDifference() {
        LocalDateTime startTime = batchService.getLatestBatchStartTime();
        LocalDateTime finishTime = batchService.getLatestBatchFinishTime();

        Duration duration = Duration.between(startTime, finishTime);

        long seconds = duration.getSeconds();

        System.out.println("Duration between start and finish:" + seconds);

        if (seconds != 0) {
            return ResponseEntity.ok(seconds);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/latestBatchQuantity")
    public ResponseEntity<Float> getLatestBatchQuantity() {
        Float latestBatchStatus = batchService.getLatestBatchQuantity();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latestBatchSpeedNormalised")
    public ResponseEntity<Float> getLatestBatchSpeedNormalised() {
        Float latestBatchStatus = batchService.getLatestBatchSpeedNormalised();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latestBatchSpeedActual")
    public ResponseEntity<Float> getLatestBatchSpeedActual() {
        Float latestBatchStatus = batchService.getLatestBatchSpeedActual();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latestBatchAcceptableProducts")
    public ResponseEntity<Integer> getLatestAcceptableProducts() {
        Integer latestBatchStatus = batchService.getLatestBatchAcceptableProducts();

        if (latestBatchStatus != null) {
            return ResponseEntity.ok(latestBatchStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latestBatchDefectProducts")
    public ResponseEntity<Integer> getLatestBatchDefectProducts() {
        Integer latestBatchStatus = batchService.getLatestBatchDefectProducts();

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