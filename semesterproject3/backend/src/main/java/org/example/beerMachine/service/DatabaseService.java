package org.example.beerMachine.service;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.example.beerMachine.OpcUA.BeerClientSingleton;
import org.example.beerMachine.OpcUA.MachineController;
import org.example.beerMachine.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class DatabaseService {
    private final BatchService batchService;
    OpcUaClient client;
    MachineController machineController = new MachineController();

    @Autowired
    public DatabaseService(BatchService batchService) {
        this.batchService = batchService;
    }

    public void start() throws ExecutionException, InterruptedException {
        StatusCode statusCode = null;
        Boolean started = machineController.startMachine();
        if (started) {
            // Set other necessary values for the batch
            int recipe = 1; // Example recipe value
            int quantity = 100; // Example quantity value
            int machineSpeed = 50; // Example machineSpeed value

            // Create a new Batch object
            Batch newBatch = new Batch(recipe, quantity, machineSpeed);

            // Add the new batch to the database
            batchService.addNewBatch(newBatch);
        }

    }
}
