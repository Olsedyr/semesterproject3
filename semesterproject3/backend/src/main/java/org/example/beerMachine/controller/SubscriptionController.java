package org.example.beerMachine.controller;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.example.beerMachine.service.subscriptionServices.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/opcua")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

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

    public SubscriptionController(ProductsProcessedSub productsProcessedSub, ProductsDefectiveSub productsDefectiveSub,
                                  StateCurrentSub stateCurrentSub, RecipeCurrentSub recipeCurrentSub, RecipeNextSub recipeNextSub,
                                  SensorHumiditySub sensorHumiditySub, SensorTemperatureSub sensorTemperatureSub,
                                  SensorVibrationSub sensorVibrationSub, BatchIdCurrentSub batchIdCurrentSub,
                                  BatchIdNextSub batchIdNextSub, MachineSpeedCurrentSub machineSpeedCurrentSub,
                                  MachineSpeedCurrentProductsPerMinuteSub machineSpeedCurrentProductsPerMinuteSub,
                                  MachineSpeedNextSub machineSpeedNextSub, QuantityCurrentSub quantityCurrentSub,
                                  QuantityNextSub quantityNextSub, IngredientBarley ingredientBarley, IngredientHops ingredientHops,
                                  IngredientMalt ingredientMalt, IngredientWheat ingredientWheat, IngredientYeast ingredientYeast,
                                  MaintenanceCounter maintenanceCounter, MaintenanceState maintenanceState,
                                  MaintenanceTrigger maintenanceTrigger) {
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
    }

    @GetMapping("/productsProcessedSub") //::Program:Cube.Admin.ProdProcessedCount
    public Object getProductsProcessedSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        return productsProcessedSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/productsDefectiveSub")
    public Object getProductsDefectiveSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        return productsDefectiveSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/stateCurrentSub")
    public Object getStateCurrentSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        return stateCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/recipeCurrentSub")
    public Object getRecipeCurrentSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Admin.Parameter[0].Value");
        return recipeCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/recipeNextSub")
    public Object getRecipeNextSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
        return recipeNextSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/sensorHumiditySub")
    public Object getSensorHumiditySubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
        return sensorHumiditySub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/sensorTemperatureSub")
    public Object getSensorTemperatureSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
        return sensorTemperatureSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/sensorVibrationSub")
    public Object getSensorVibrationSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
        return sensorVibrationSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/batchIdCurrentSub")
    public Object getBatchIdCurrentSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
        return batchIdCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/batchIdNextSub")
    public Object getBatchIdNextSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
        return batchIdNextSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/machineSpeedCurrentSub")
    public Object getMachineSpeedCurrentSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.CurMachSpeed");
        return machineSpeedCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/machineSpeedCurrentProductsPerMinuteSub")
    public Object getMachineSpeedCurrentProductsPerMinuteSub() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.MachSpeed");
        return machineSpeedCurrentProductsPerMinuteSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/machineSpeedNextSub")
    public Object getMachineSpeedNextSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Command.MachSpeed");
        return machineSpeedNextSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/quantityCurrentSub")
    public Object getQuantityCurrentSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
        return quantityCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/quantityNextSub")
    public Object getQuantityNextSubValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
        return quantityNextSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/ingredientBarley")
    public Object getIngredientBarleyValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Barley");
        return ingredientBarley.getNodeValue(parsedNodeId);
    }

    @GetMapping("/ingredientHops")
    public Object getIngredientHopsValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Hops");
        return ingredientHops.getNodeValue(parsedNodeId);
    }

    @GetMapping("/ingredientMalt")
    public Object getIngredientMaltValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Malt");
        return ingredientMalt.getNodeValue(parsedNodeId);
    }

    @GetMapping("/ingredientWheat")
    public Object getIngredientWheatValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Wheat");
        return ingredientWheat.getNodeValue(parsedNodeId);
    }

    @GetMapping("/ingredientYeast")
    public Object getIngredientYeastValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Inventory.Yeast");
        return ingredientYeast.getNodeValue(parsedNodeId);
    }

    @GetMapping("/maintenanceCounter")
    public Object getMaintenanceCounterValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Maintenance.Counter");
        return maintenanceCounter.getNodeValue(parsedNodeId);
    }

    @GetMapping("/maintenanceState")
    public Object getMaintenanceStateValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Maintenance.State");
        return maintenanceState.getNodeValue(parsedNodeId);
    }

    @GetMapping("/maintenanceTrigger")
    public Object getMaintenanceTriggerValue() {
        NodeId parsedNodeId = new NodeId(6, "::Program:Maintenance.Trigger");
        return maintenanceTrigger.getNodeValue(parsedNodeId);
    }
}
