package com.example.backend;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {
 @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        System.out.println("URI: " + request.getURI());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Headers: " + request.getHeaders());
        System.out.println("Request Body: " + new String(body, "UTF-8"));
        return execution.execute(request, body);
    }
}

