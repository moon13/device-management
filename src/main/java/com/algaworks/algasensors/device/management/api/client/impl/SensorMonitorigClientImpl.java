package com.algaworks.algasensors.device.management.api.client.impl;

//import com.algaworks.algasensors.temperature.monitoring.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClientBAdGatewayException;
import io.hypersistence.tsid.TSID;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitorigClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;

    public SensorMonitorigClientImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082")
                .defaultStatusHandler(HttpStatusCode::isError, (request,response)->{
                    throw new SensorMonitoringClientBAdGatewayException();
                })
                .build();
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
