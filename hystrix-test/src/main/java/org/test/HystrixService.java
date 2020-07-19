package org.test;

import com.netflix.hystrix.*;

public class HystrixService extends HystrixCommand<String> {
    private final Boolean raiseException;

    public HystrixService(Boolean raiseException) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("group"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("key"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(1).withMaximumSize(1))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerEnabled(true)
                                .withCircuitBreakerRequestVolumeThreshold(30)
                                .withCircuitBreakerErrorThresholdPercentage(40)
                                .withCircuitBreakerSleepWindowInMilliseconds(10000)
                                )
        );
        this.raiseException = raiseException;
    }


    protected String run() throws Exception {
        if (raiseException) {
            throw new Exception();
        }
        return "Primary";
    }

    @Override
    protected String getFallback() {
        if (isCircuitBreakerOpen()) {
            return "Circuit Open";
        }
        return "Fallback";
    }
}
