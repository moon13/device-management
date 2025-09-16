package com.algaworks.algasensors.device.management.api.client.impl;

//import com.algaworks.algasensors.temperature.monitoring.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.RestClientFactory;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClientBAdGatewayException;
import com.algaworks.algasensors.device.management.api.model.SensorMonitoringOutput;
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

    public SensorMonitorigClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
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

    @Override
    public SensorMonitoringOutput getDetail(TSID sensorId) {
        return restClient.get()
                .uri("/api/sensors/{sensorID}/monitoring",sensorId)
                .retrieve()
                .body(SensorMonitoringOutput.class);
    }
}
