package com.zz.hytrix.command;

import com.netflix.hystrix.*;
import com.zz.hytrix.model.Request;
import com.zz.hytrix.model.Response;
import com.zz.hytrix.service.IService;

/**
 * @Author zhangzhen
 * @create 2023/1/19 21:39
 */
public class MyHystrixCommand extends HystrixCommand<Response> {

    private IService service;
    private Request request;

    public MyHystrixCommand(IService service, Request request) {
        super(
                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("serviceGroupKey"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("serviceCommandKey"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("serviceThreadPool"))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                // 熔断器从关闭到打开的健康阈值
                                .withCircuitBreakerErrorThresholdPercentage(60)
                                // 熔断器从打开到扳开的时间窗口
                                .withCircuitBreakerSleepWindowInMilliseconds(3000)
                        )
                        // 线程池核心线程数 -- threadPoolKey一样的话，线程池会服用
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(20))
        );
        this.service = service;
        this.request = request;
    }

    @Override
    protected Response run() throws Exception {
        return service.sayHello(request);
    }

    @Override
    protected Response getFallback() {
        return super.getFallback();
    }
}
