package org.example.beerMachine.controller;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.example.beerMachine.model.Batch;
import org.example.beerMachine.repository.BatchRepository;
import org.example.beerMachine.service.subscriptionServices.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/opcua")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionToDatabaseController {

    private final ProductsProcessedSub productsProcessedSub;
    private final ProductsDefectiveSub productsDefectiveSub;
    private final StateCurrentSub stateCurrentSub;
    private final RecipeCurrentSub recipeCurrentSub;
    private final RecipeNextSub recipeNextSub;
    private final SensorHumiditySub sensorHumiditySub;
    private final SensorTemperatureSub sensorTemperatureSub;
    private final SensorVibrationSub sensorVibrationSub;
    private final BatchIdCurrentSub batchIdCurrentSub;
    private final BatchIdNextSub batchIdNextSub;
    private final MachineSpeedCurrentSub machineSpeedCurrentSub;
    private final MachineSpeedCurrentProductsPerMinuteSub machineSpeedCurrentProductsPerMinuteSub;
    private final MachineSpeedNextSub machineSpeedNextSub;
    private final QuantityCurrentSub quantityCurrentSub;
    private final QuantityNextSub quantityNextSub;
    private final IngredientBarley ingredientBarley;
    private final IngredientHops ingredientHops;
    private final IngredientMalt ingredientMalt;
    private final IngredientWheat ingredientWheat;
    private final IngredientYeast ingredientYeast;
    private final MaintenanceCounter maintenanceCounter;
    private final MaintenanceState maintenanceState;
    private final MaintenanceTrigger maintenanceTrigger;

    private final BatchRepository batchRepository;

    public SubscriptionToDatabaseController(ProductsProcessedSub productsProcessedSub, ProductsDefectiveSub productsDefectiveSub,
                                            StateCurrentSub stateCurrentSub, RecipeCurrentSub recipeCurrentSub, RecipeNextSub recipeNextSub,
                                            SensorHumiditySub sensorHumiditySub, SensorTemperatureSub sensorTemperatureSub,
                                            SensorVibrationSub sensorVibrationSub, BatchIdCurrentSub batchIdCurrentSub,
                                            BatchIdNextSub batchIdNextSub, MachineSpeedCurrentSub machineSpeedCurrentSub,
                                            MachineSpeedCurrentProductsPerMinuteSub machineSpeedCurrentProductsPerMinuteSub,
                                            MachineSpeedNextSub machineSpeedNextSub, QuantityCurrentSub quantityCurrentSub,
                                            QuantityNextSub quantityNextSub, IngredientBarley ingredientBarley, IngredientHops ingredientHops,
                                            IngredientMalt ingredientMalt, IngredientWheat ingredientWheat, IngredientYeast ingredientYeast,
                                            MaintenanceCounter maintenanceCounter, MaintenanceState maintenanceState,
                                            MaintenanceTrigger maintenanceTrigger, BatchRepository batchRepository) {
        this.productsProcessedSub = productsProcessedSub;
        this.productsDefectiveSub = productsDefectiveSub;
        this.stateCurrentSub = stateCurrentSub;
        this.recipeCurrentSub = recipeCurrentSub;
        this.recipeNextSub = recipeNextSub;
        this.sensorHumiditySub = sensorHumiditySub;
        this.sensorTemperatureSub = sensorTemperatureSub;
        this.sensorVibrationSub = sensorVibrationSub;
        this.batchIdCurrentSub = batchIdCurrentSub;
        this.batchIdNextSub = batchIdNextSub;
        this.machineSpeedCurrentSub = machineSpeedCurrentSub;
        this.machineSpeedCurrentProductsPerMinuteSub = machineSpeedCurrentProductsPerMinuteSub;
        this.machineSpeedNextSub = machineSpeedNextSub;
        this.quantityCurrentSub = quantityCurrentSub;
        this.quantityNextSub = quantityNextSub;
        this.ingredientBarley = ingredientBarley;
        this.ingredientHops = ingredientHops;
        this.ingredientMalt = ingredientMalt;
        this.ingredientWheat = ingredientWheat;
        this.ingredientYeast = ingredientYeast;
        this.maintenanceCounter = maintenanceCounter;
        this.maintenanceState = maintenanceState;
        this.maintenanceTrigger = maintenanceTrigger;
        this.batchRepository = batchRepository;
    }

    @PostMapping("/saveBatch")
    public void saveBatchValues() {
        // Fetch values from OPC UA nodes
        int processedProducts = convertToInt(getProductsProcessedSubValue());
        int defectProducts = convertToInt(getProductsDefectiveSubValue());
        int acceptableProducts = processedProducts-defectProducts;
        float humiditySubValue = convertToFloat(getSensorHumiditySubValue());
        float temperatureSubValue = convertToFloat(getSensorTemperatureSubValue());
        float vibrationSubValue = convertToFloat(getSensorVibrationSubValue());
        float ingredientBarley = convertToFloat(getIngredientBarleyValue());
        float ingredientHops = convertToFloat(getIngredientHopsValue());
        float ingredientMalt = convertToFloat(getIngredientMaltValue());
        float ingredientWheat = convertToFloat(getIngredientWheatValue());
        float ingredientYeast = convertToFloat(getIngredientYeastValue());
        //System.out.println("ingredientHops: " + ingredientHops);


        // Find the latest batch in the database
        Optional<Batch> latestBatchOptional = batchRepository.findFirstByOrderByStartTimeDesc();

        // If a latest batch exists, update its values
        if (latestBatchOptional.isPresent()) {
            Batch latestBatch = latestBatchOptional.get();
            if (latestBatch.getStatus().equals("started")) {
                latestBatch.setProcessedProductsTotal(acceptableProducts);
                latestBatch.setDefectProducts(defectProducts);
                latestBatch.setAcceptableProducts(acceptableProducts);
                latestBatch.setIngredientBarleyStop(ingredientBarley);
                latestBatch.setIngredientHopsStop(ingredientHops);
                latestBatch.setIngredientMaltStop(ingredientMalt);
                latestBatch.setIngredientWheatStop(ingredientWheat);
                latestBatch.setIngredientYeastStop(ingredientYeast);

                // Update highest/lowest sensor values
                if (humiditySubValue > latestBatch.getHumidityHighest()) {
                    latestBatch.setHumidityHighest(humiditySubValue);
                }
                if (humiditySubValue < latestBatch.getHumidityLowest()) {
                    latestBatch.setHumidityLowest(humiditySubValue);
                }
                if (temperatureSubValue > latestBatch.getTemperatureHighest()) {
                    latestBatch.setTemperatureHighest(temperatureSubValue);
                }
                if (temperatureSubValue < latestBatch.getTemperatureLowest()) {
                    latestBatch.setTemperatureLowest(temperatureSubValue);
                }
                if (vibrationSubValue > latestBatch.getVibrationHighest()) {
                    latestBatch.setVibrationHighest(vibrationSubValue);
                }
                if (vibrationSubValue < latestBatch.getVibrationLowest()) {
                    latestBatch.setVibrationLowest(vibrationSubValue);
                }

            }
            // Save the updated batch entity
            batchRepository.save(latestBatch);
        } else {
            // If no batch exists
            System.out.println("No existing batch found. Values not updated.");
        }
    }

    @PostMapping("/saveBatchStatusFinished")
    public void checkAndSaveBatchStatus() {
        // Fetch values from OPC UA nodes
        int currentStatus = convertToInt(getStateCurrentSubValue());

        // Find the latest batch in the database
        Optional<Batch> latestBatchOptional = batchRepository.findFirstByOrderByStartTimeDesc();

        if (latestBatchOptional.isPresent()) {
            // If a latest batch exists, update its values
            Batch latestBatch = latestBatchOptional.get();

            // If the batch is completed, set it to "completed" in the database
            if (currentStatus == 17 && latestBatch.getStatus().equals("started")
                    && latestBatch.getProcessedProductsTotal() == latestBatch.getQuantity()) {
                latestBatch.setStatus("completed");
                latestBatch.setFinishTime(LocalDateTime.now());
            }

            // Save the updated batch entity
            batchRepository.save(latestBatch);
        } else {
            // If no batch exists
            System.out.println("No existing batch found. Values not updated.");
        }
    }


    private int convertToInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            return 0; // Change to handle default value or throw an exception
        }
    }

    private float convertToFloat(Object value) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else {
            return 0.0f; // Change to handle default value or throw an exception
        }
    }

    private Object getProductsProcessedSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        return productsProcessedSub.getNodeValue(parsedNodeId);
    }

    private Object getProductsDefectiveSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        return productsDefectiveSub.getNodeValue(parsedNodeId);
    }


    private Object getStateCurrentSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        return stateCurrentSub.getNodeValue(parsedNodeId);
    }

    public Object getSensorHumiditySubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
        return sensorHumiditySub.getNodeValue(parsedNodeId);
    }


    public Object getSensorTemperatureSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
        return sensorTemperatureSub.getNodeValue(parsedNodeId);
    }


    public Object getSensorVibrationSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
        return sensorVibrationSub.getNodeValue(parsedNodeId);
    }


    public Object getIngredientBarleyValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Barley");
        return ingredientBarley.getNodeValue(parsedNodeId);
    }

    public Object getIngredientHopsValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Hops");
        return ingredientHops.getNodeValue(parsedNodeId);
    }

    public Object getIngredientMaltValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Malt");
        return ingredientMalt.getNodeValue(parsedNodeId);
    }

    public Object getIngredientWheatValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Wheat");
        return ingredientWheat.getNodeValue(parsedNodeId);
    }

    public Object getIngredientYeastValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Yeast");
        return ingredientYeast.getNodeValue(parsedNodeId);
    }

}
