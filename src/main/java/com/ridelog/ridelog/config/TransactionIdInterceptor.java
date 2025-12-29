package com.ridelog.ridelog.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class TransactionIdInterceptor implements HandlerInterceptor {

    public static final String TRANSACTION_ID_HEADER = "X-Transaction-Id";
    public static final String MDC_KEY = "transactionId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String transactionId = request.getHeader(TRANSACTION_ID_HEADER);

        if (transactionId == null || transactionId.isEmpty()) {
            transactionId = UUID.randomUUID().toString();
        }

        // Add to MDC so it appears in all logs for this request
        MDC.put(MDC_KEY, transactionId);

        // Optionally, add the transaction ID to the response header
        response.setHeader(TRANSACTION_ID_HEADER, transactionId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Clean up MDC to prevent leakage between threads
        MDC.remove(MDC_KEY);
    }
}
