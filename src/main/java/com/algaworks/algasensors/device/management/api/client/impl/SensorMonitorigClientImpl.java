package com.algaworks.algasensors.device.management.api.client.impl;

//import com.algaworks.algasensors.temperature.monitoring.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClientBAdGatewayException;
import io.hypersistence.tsid.TSID;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class SensorMonitorigClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;

    public SensorMonitorigClientImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082")
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request,response)->{
                    throw new SensorMonitoringClientBAdGatewayException();
                })
                .build();
    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(3));
        return factory;
    }

    @Override
    public void enableMonitoring(TSID sensorId) {
       restClient.put()
               .uri("/api/sensors/{sensorID}/monitoring/enable",sensorId)
               .retrieve()
               .toBodilessEntity();
    }

    @Override
    public void disasbleMonitoring(TSID sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorID}/monitoring/enable",sensorId)
                .retrieve()
                .toBodilessEntity();
    }
}
