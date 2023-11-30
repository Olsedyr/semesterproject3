package org.example.beerMachine.controller;
import org.example.beerMachine.service.BatchService;
import org.example.beerMachine.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="api/batch")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class BatchController {
	private final BatchService batchService;

	@Autowired
	public BatchController(BatchService batchService) {
		this.batchService = batchService;
	}

	@GetMapping
	public List<Batch> getBatches(){
		return batchService.getBatches();
	}

	@PostMapping
	public void makeNewBatch(@RequestBody Batch batch){
		batchService.addNewBatch(batch);
	}

	@DeleteMapping(path = "{batchId}")
	public void deleteBatch(@PathVariable("batchId")Long batchId){
		batchService.deleteBatch(batchId);
	}
	// Update batches in the database
	@PutMapping(path = "update/{batchId}")
	public void updateBatch(
			@PathVariable("batchId") Long batchId,
			@RequestParam(required = false) Integer recipe,
			@RequestParam(required = false) Integer quantity){
		batchService.updateBatch(batchId,recipe,quantity);
	}
	@PutMapping(path = "updateStatus")
	public void updateBatchStatus(
			@RequestParam String status){
		batchService.updateBatchStatus(status);
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


}