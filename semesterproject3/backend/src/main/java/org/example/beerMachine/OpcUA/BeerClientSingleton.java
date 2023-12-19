package org.example.beerMachine.OpcUA;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;

import java.util.List;

public class BeerClientSingleton {

    private static OpcUaClient instance;

    private BeerClientSingleton() {
    }

    public static synchronized OpcUaClient getInstance() {
        if (instance == null) {
            try {
                instance = createAndConnectOPCUAClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private static OpcUaClient createAndConnectOPCUAClient() throws Exception {
        //String serverEndpointUrl = "opc.tcp://127.0.0.1:4840"; // Software simulation
        //String hostname = "127.0.0.1"; // Software simulation
        String serverEndpointUrl = "opc.tcp://192.168.0.122:4840"; // Machine Simulation
        String hostname = "192.168.0.122"; // Machine Simulation

        int port = 4840;

        OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();

        List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(serverEndpointUrl).get();
        EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), hostname, port);

        if (configPoint == null) {
            throw new Exception("No suitable endpoint found");
        }

        cfg.setEndpoint(configPoint);

        OpcUaClient client = OpcUaClient.create(cfg.build());

        return (OpcUaClient) client.connect().get();
    }
}
