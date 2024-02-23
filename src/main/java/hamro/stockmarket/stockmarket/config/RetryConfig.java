package hamro.stockmarket.stockmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Configuration class for setting up retry operations in the application. This class
 * defines a RetryOperationsInterceptor bean with custom retry and backoff policies.
 * Author: [Aashish Karki]
 */
@Configuration
@EnableRetry
public class RetryConfig {

  /**
   * Bean definition for RetryOperationsInterceptor. Configures a RetryTemplate with a
   * SimpleRetryPolicy and ExponentialBackOffPolicy.
   *
   * @return RetryOperationsInterceptor bean
   */
  @Bean(name = "retryData")
  public RetryOperationsInterceptor retryOperationsInterceptor() {
    RetryTemplate retryTemplate = new RetryTemplate();

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(3);

    ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
    exponentialBackOffPolicy.setInitialInterval(500);
    exponentialBackOffPolicy.setMultiplier(2);

    retryTemplate.setRetryPolicy(retryPolicy);
    retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

    return RetryInterceptorBuilder.stateless().retryOperations(retryTemplate).build();
  }
}
