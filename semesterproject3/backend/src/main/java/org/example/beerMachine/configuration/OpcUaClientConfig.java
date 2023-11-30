package org.example.beerMachine.configuration;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.example.beerMachine.OpcUA.BeerClientSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpcUaClientConfig {

    OpcUaClient client;
    @Bean
    public OpcUaClient opcUaClient() {
        return client = BeerClientSingleton.getInstance();

    }
}