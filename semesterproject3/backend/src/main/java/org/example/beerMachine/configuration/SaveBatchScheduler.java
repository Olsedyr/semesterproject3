package org.example.beerMachine.configuration;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.example.beerMachine.model.Batch;
import org.example.beerMachine.model.SensorData;
import org.example.beerMachine.repository.BatchRepository;
import org.example.beerMachine.repository.SensorDataRepository;
import org.example.beerMachine.service.subscriptionServices.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class SaveBatchScheduler {

    private final ProductsProcessedSub productsProcessedSub;
    private final ProductsDefectiveSub productsDefectiveSub;
    private final StateCurrentSub stateCurrentSub;
    private final SensorHumiditySub sensorHumiditySub;
    private final SensorTemperatureSub sensorTemperatureSub;
    private final SensorVibrationSub sensorVibrationSub;
    private final IngredientBarley ingredientBarley;
    private final IngredientHops ingredientHops;
    private final IngredientMalt ingredientMalt;
    private final IngredientWheat ingredientWheat;
    private final IngredientYeast ingredientYeast;
    private final MaintenanceCounter maintenanceCounter;
    private final MaintenanceState maintenanceState;
    private final MaintenanceTrigger maintenanceTrigger;

    private final BatchRepository batchRepository;
    private final SensorDataRepository sensorDataRepository;

    public SaveBatchScheduler(ProductsProcessedSub productsProcessedSub, ProductsDefectiveSub productsDefectiveSub,
                              StateCurrentSub stateCurrentSub, SensorHumiditySub sensorHumiditySub, SensorTemperatureSub sensorTemperatureSub,
                              SensorVibrationSub sensorVibrationSub, IngredientBarley ingredientBarley,
                              IngredientHops ingredientHops, IngredientMalt ingredientMalt,
                              IngredientWheat ingredientWheat, IngredientYeast ingredientYeast, MaintenanceCounter maintenanceCounter,
                              MaintenanceState maintenanceState, MaintenanceTrigger maintenanceTrigger,
                              BatchRepository batchRepository, SensorDataRepository sensorDataRepository
    ) {
        this.productsProcessedSub = productsProcessedSub;
        this.productsDefectiveSub = productsDefectiveSub;
        this.stateCurrentSub = stateCurrentSub;
        this.sensorHumiditySub = sensorHumiditySub;
        this.sensorTemperatureSub = sensorTemperatureSub;
        this.sensorVibrationSub = sensorVibrationSub;
        this.ingredientBarley = ingredientBarley;
        this.ingredientHops = ingredientHops;
        this.ingredientMalt = ingredientMalt;
        this.ingredientWheat = ingredientWheat;
        this.ingredientYeast = ingredientYeast;
        this.maintenanceCounter = maintenanceCounter;
        this.maintenanceState = maintenanceState;
        this.maintenanceTrigger = maintenanceTrigger;
        this.batchRepository = batchRepository;
        this.sensorDataRepository = sensorDataRepository;
    }
    @Scheduled(fixedRate = 5000) // Run every 5 second
    public void saveSensorData() {
        // Fetch values from OPC UA nodes
        float humiditySubValue = convertToFloat(getSensorHumiditySubValue());
        float temperatureSubValue = convertToFloat(getSensorTemperatureSubValue());
        float vibrationSubValue = convertToFloat(getSensorVibrationSubValue());

        // Find the latest batch in the database
        Optional<Batch> latestBatchOptional = batchRepository.findFirstByOrderByStartTimeDesc();

        if (latestBatchOptional.isPresent()) {
            Batch latestBatch = latestBatchOptional.get();
            if (latestBatch.getStatus().equals("started")) {
                SensorData sensorData = new SensorData();
                sensorData.setBatchId(latestBatchOptional.get().getId());
                sensorData.setHumidity(humiditySubValue);
                sensorData.setTemperature(temperatureSubValue);
                sensorData.setVibration(vibrationSubValue);
                sensorDataRepository.save(sensorData);
            }
        }

    }

    @Scheduled(fixedRate = 1000) // Run every 1 second
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
                latestBatch.setProcessedProductsTotal(processedProducts);
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

                // Mean temp, humidity and vibration of all SensorData with the latest batchId
                Float temperatureMean = sensorDataRepository.calculateTemperatureMeanByBatchId(latestBatch.getId());
                Float humidityMean = sensorDataRepository.calculateHumidityMeanByBatchId(latestBatch.getId());
                Float vibrationMean = sensorDataRepository.calculateVibrationMeanByBatchId(latestBatch.getId());

                // Update the mean sensor data of the latest batch
                latestBatch.setTemperatureMean(temperatureMean);
                latestBatch.setHumidityMean(humidityMean);
                latestBatch.setVibrationMean(vibrationMean);

            }

            // Save the updated batch entity
            batchRepository.save(latestBatch);
        } else {
            // If no batch exists
            System.out.println("No existing batch found. Values not updated.");
        }
    }
    @Scheduled(fixedRate = 1000) // Run every 1 second
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
            return 0;
        }
    }

    private float convertToFloat(Object value) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else {
            return 0.0f;
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
