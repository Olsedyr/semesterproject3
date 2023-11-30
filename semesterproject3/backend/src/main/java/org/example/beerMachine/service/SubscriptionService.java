package org.example.beerMachine.service;

import org.example.beerMachine.repository.SubscriptionRepository;
import org.example.beerMachine.service.subscriptionServices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class SubscriptionService {
    //@Autowired
    private SubscriptionRepository subscriptionRepository;

    private final ProductsAcceptableSub productsAcceptableSub;
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

    public SubscriptionService(ProductsAcceptableSub productsAcceptableSub, ProductsDefectiveSub productsDefectiveSub, StateCurrentSub stateCurrentSub, RecipeCurrentSub recipeCurrentSub, RecipeNextSub recipeNextSub, SensorHumiditySub sensorHumiditySub, SensorTemperatureSub sensorTemperatureSub, SensorVibrationSub sensorVibrationSub, BatchIdCurrentSub batchIdCurrentSub, BatchIdNextSub batchIdNextSub, MachineSpeedCurrentSub machineSpeedCurrentSub, MachineSpeedCurrentProductsPerMinuteSub machineSpeedCurrentProductsPerMinuteSub, MachineSpeedNextSub machineSpeedNextSub, QuantityCurrentSub quantityCurrentSub, QuantityNextSub quantityNextSub, IngredientBarley ingredientBarley, IngredientHops ingredientHops, IngredientMalt ingredientMalt, IngredientWheat ingredientWheat, IngredientYeast ingredientYeast, MaintenanceCounter maintenanceCounter, MaintenanceState maintenanceState, MaintenanceTrigger maintenanceTrigger) {
        this.productsAcceptableSub = productsAcceptableSub;
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
    }

//    public SubscriptionValuesDTO fetchAndSaveSubscriptionValues() {
//        // Fetch values from OPC UA subscriptions
//        Object productsAcceptableSubValue = productsAcceptableSub.getNodeValue()/* Fetch from OPC UA */;
//        Object productsDefectiveSubValue = /* Fetch from OPC UA */;
//        Object stateCurrentSubValue = /* Fetch from OPC UA */;
//        Object recipeCurrentSubValue = /* Fetch from OPC UA */;
//
//        // Create DTO and set values
//        SubscriptionValuesDTO subscriptionValuesDTO = new SubscriptionValuesDTO();
//        subscriptionValuesDTO.setProductsAcceptableSubValue(productsAcceptableSubValue);
//        subscriptionValuesDTO.setProductsDefectiveSubValue(productsDefectiveSubValue);
//        subscriptionValuesDTO.setStateCurrentSubValue(stateCurrentSubValue);
//        subscriptionValuesDTO.setRecipeCurrentSubValue(recipeCurrentSubValue);
//
//        // Save values to the database
//        return subscriptionRepository.save(subscriptionValuesDTO);
//    }
}