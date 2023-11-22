package org.example.beerMachine.controller;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.example.beerMachine.OpcUA.subscriptions.DefectProductsSub;
import org.example.beerMachine.OpcUA.subscriptions.ProducedProductsSub;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/opcua")
@CrossOrigin(origins = "http://localhost:3000")
public class OpcUaController {

    private final ProducedProductsSub producedProductsSub;
    private final DefectProductsSub defectProductsSub;

    public OpcUaController(ProducedProductsSub subscriptionService, DefectProductsSub defectProductsSub) {
        this.producedProductsSub = subscriptionService;
        this.defectProductsSub = defectProductsSub;
    }

    @GetMapping("/producedNodeValue/{nodeId}")
    public Object getProducedProductsSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return producedProductsSub.getNodeValue(parsedNodeId);
    }

    @GetMapping("/defectNodeValue/{nodeId}")
    public Object getDefectProductsSubValue(@PathVariable String nodeId) {
        NodeId parsedNodeId = new NodeId(6, nodeId);
        return defectProductsSub.getNodeValue(parsedNodeId);
    }
}
