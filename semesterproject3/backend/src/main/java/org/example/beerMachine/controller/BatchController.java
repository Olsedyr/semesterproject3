package org.example.beerMachine.controller;

import org.example.beerMachine.service.BatchService;
import org.example.beerMachine.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
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
	@PutMapping(path = "{batchId}")
	public void updateBatch(
			@PathVariable("batchId") Long batchId,
			@RequestParam(required = false) Integer recipe,
			@RequestParam(required = false) Integer quantity,
			@RequestParam(required = false) Integer machineSpeed){
		batchService.updateStudent(batchId,recipe,quantity,machineSpeed);
	}

}
