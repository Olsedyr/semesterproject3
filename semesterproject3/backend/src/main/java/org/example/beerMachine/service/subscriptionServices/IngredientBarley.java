package org.example.beerMachine.service.subscriptionServices;

import jakarta.annotation.PostConstruct;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.example.beerMachine.OpcUA.BeerClientSingleton;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class IngredientBarley {

    final OpcUaClient client;
    private final Map<NodeId, Object> nodeValues;

    public IngredientBarley() {
        this.client = BeerClientSingleton.getInstance();
        this.nodeValues = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void createSubscription() {
        try {
            // Adapted from OPC UA files from the course Industrial cyber-physical systems
            /* Node endpoints */
            NodeId[] nodeIdsToMonitor = {
                    new NodeId(6, "::Program:Inventory.Barley")
            };

            List<MonitoredItemCreateRequest> monitoredItemCreateRequests = new ArrayList<>();


            // create a subscription @ 1000ms
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();

            // important: client handle must be unique per item
            UInteger clientHandle = subscription.getSubscriptionId();
            MonitoringParameters parameters = new MonitoringParameters(
                    clientHandle,
                    1000.0,     // sampling interval
                    null,       // filter, null means use default
                    Unsigned.uint(10),   // queue size
                    true        // discard oldest
            );

            // creation request
            for (NodeId nodeId : nodeIdsToMonitor) {
                // Define what to read for each NodeId
                ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

                // Create a MonitoredItemCreateRequest for each NodeId
                MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                        readValueId,
                        MonitoringMode.Reporting,
                        parameters
                );

                monitoredItemCreateRequests.add(request);
            }


            // setting the consumer after the subscription creation
            UaSubscription.ItemCreationCallback onItemCreated = (item, id) -> item.setValueConsumer(this::onSubscriptionValue);


            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, monitoredItemCreateRequests, onItemCreated).get();

            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    System.out.println("item created for nodeId = " + item.getReadValueId().getNodeId());
                } else {
                    System.out.println("failed to create item for nodeId = " + item.getReadValueId().getNodeId() + " (status=" + item.getStatusCode() + ")");
                }
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }


    private void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        Object newValue = value.getValue().getValue();
        NodeId nodeId = item.getReadValueId().getNodeId();

        //System.out.println("Received value for Node " + nodeId + ": " + newValue);

        nodeValues.put(nodeId, newValue);
    }


    public Object getNodeValue(NodeId nodeId) {
        return nodeValues.getOrDefault(nodeId, "No data available");
    }
}
