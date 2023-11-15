package org.example.beerMachine.OpcUA;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

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
        String serverEndpointUrl = "opc.tcp://127.0.0.1:4840";
        String username = "sdu";
        String password = "1234";

        OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
        UsernameProvider identityProvider = new UsernameProvider(username, password);

        List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(serverEndpointUrl).get();

        EndpointDescription selectedEndpoint = null;
        for (EndpointDescription endpoint : endpoints) {
            //System.out.println(endpoint.getSecurityMode().name());
            if (endpoint.getSecurityMode().name().equals("None")) {
                selectedEndpoint = endpoint;
                break;
            }
        }

        if (selectedEndpoint == null) {
            throw new Exception("No suitable endpoint found");
        }

        cfg.setEndpoint(selectedEndpoint);
        OpcUaClientConfig config = cfg.setIdentityProvider(identityProvider).build();
        OpcUaClient client = OpcUaClient.create(config);

        return (OpcUaClient) client.connect().get();
    }
}
