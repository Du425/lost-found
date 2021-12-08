package com.du.lostfoundmasterfk.config.request;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Autowired
    MyFormHttpMessageConverter myFormHttpMessageConverter;

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(2000);
        httpRequestFactory.setConnectTimeout(10000);
        httpRequestFactory.setReadTimeout(72000);
        HttpClient client = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy()).build();
        httpRequestFactory.setHttpClient(client);
        restTemplate.setRequestFactory(httpRequestFactory);

        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
        httpMessageConverters.add(myFormHttpMessageConverter);
        restTemplate.setMessageConverters(httpMessageConverters);

        return restTemplate;
    }
}
