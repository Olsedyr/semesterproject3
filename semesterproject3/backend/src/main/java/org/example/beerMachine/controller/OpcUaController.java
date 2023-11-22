package org.example.beerMachine.controller;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.example.beerMachine.OpcUA.subscriptions.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/opcua")
@CrossOrigin(origins = "http://localhost:3000")
public class OpcUaController {

    private final ProductsAcceptableSub productsAcceptableSub;
    private final ProductsDefectiveSub productsDefectiveSub;
    private final StateCurrentSub stateCurrentSub;
    private final RecipeCurrentSub recipeCurrentSub;
    private final SensorHumiditySub sensorHumiditySub;
    private final SensorTemperatureSub sensorTemperatureSub;
    private final SensorVibrationSub sensorVibrationSub;
    private final BatchIdCurrentSub batchIdCurrentSub;
    private final MachineSpeedCurrentSub machineSpeedCurrentSub;
    private final QuantityCurrentSub quantityCurrentSub;


    public OpcUaController(ProductsAcceptableSub productsAcceptableSub, ProductsDefectiveSub productsDefectiveSub,
                           StateCurrentSub stateCurrentSub, RecipeCurrentSub recipeCurrentSub,
                           SensorHumiditySub sensorHumiditySub, SensorTemperatureSub sensorTemperatureSub,
                           SensorVibrationSub sensorVibrationSub, BatchIdCurrentSub batchIdCurrentSub,
                           MachineSpeedCurrentSub machineSpeedCurrentSub, QuantityCurrentSub quantityCurrentSub) {
        this.productsAcceptableSub = productsAcceptableSub;
        this.productsDefectiveSub = productsDefectiveSub;
        this.stateCurrentSub = stateCurrentSub;
        this.recipeCurrentSub = recipeCurrentSub;
        this.sensorHumiditySub = sensorHumiditySub;
        this.sensorTemperatureSub = sensorTemperatureSub;
        this.sensorVibrationSub = sensorVibrationSub;
        this.batchIdCurrentSub = batchIdCurrentSub;
        this.machineSpeedCurrentSub = machineSpeedCurrentSub;
        this.quantityCurrentSub = quantityCurrentSub;
    }

    @GetMapping("/productsAcceptableSub/{nodeId}")
    public Object getProductsAcceptableSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return productsAcceptableSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/productsDefectiveSub/{nodeId}")
    public Object getProductsDefectiveSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return productsDefectiveSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/stateCurrentSub/{nodeId}")
    public Object getStateCurrentSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return stateCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/recipeCurrentSub/{nodeId}") //http://localhost:8080/opcua/recipeCurrentSub/::Program:Cube.Admin.Parameter%5B0%5D.Value
    public Object getRecipeCurrentSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return recipeCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/sensorHumiditySub/{nodeId}") //http://localhost:8080/opcua/sensorHumiditySub/::Program:Cube.Status.Parameter%5B2%5D.Value
    public Object getSensorHumiditySubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return sensorHumiditySub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/sensorTemperatureSub/{nodeId}") //http://localhost:8080/opcua/sensorTemperatureSub/::Program:Cube.Status.Parameter%5B3%5D.Value
    public Object getSensorTemperatureSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return sensorTemperatureSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/sensorVibrationSub/{nodeId}") //http://localhost:8080/opcua/sensorVibrationSub/::Program:Cube.Status.Parameter%5B4%5D.Value
    public Object getSensorVibrationSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return sensorVibrationSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/batchIdCurrentSub/{nodeId}")
    public Object getBatchIdCurrentSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return batchIdCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/machineSpeedCurrentSub/{nodeId}")
    public Object getMachineSpeedCurrentSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return machineSpeedCurrentSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/quantityCurrentSub/{nodeId}") //http://localhost:8080/opcua/quantityCurrentSub/::Program:Cube.Status.Parameter%5B1%5D.Value
    public Object getQuantityCurrentSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return quantityCurrentSub.getNodeValue(parsedNodeId);
    }
}
