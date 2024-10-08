package dev.ubaid.rc.config;

import dev.ubaid.rc.rest.client.RandomYesNoClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {
    
    @Bean
    RandomYesNoClient randomYesNoClient() {

        ConnectionConfig connectionConfig = ConnectionConfig
                .custom()
                .setConnectTimeout(Timeout.ofSeconds(10))
                .setSocketTimeout(Timeout.ofMinutes(1))
                .setValidateAfterInactivity(Timeout.ofSeconds(2))
                .setTimeToLive(Timeout.ofMinutes(10))
                .build();

        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder
                .create()
                .setMaxConnTotal(1000)
                .setMaxConnPerRoute(250)
                .setDefaultConnectionConfig(connectionConfig)
                .build();

        
        RequestConfig requestConfig =  RequestConfig
                .custom()
                .setConnectionRequestTimeout(Timeout.ofMinutes(10))
                .build();

        HttpClient httpClient = HttpClients
                .custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .evictExpiredConnections()
                .evictIdleConnections(Timeout.ofSeconds(60))
                .build();
        
        RestClient restClient = RestClient.builder().requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient)).baseUrl("https://yesno.wtf").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(RandomYesNoClient.class);
    }
    
}
