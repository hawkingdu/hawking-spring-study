package edu.hawking.service;

import edu.hawking.framework.Component;

/**
 * 杜皓君 created by 2021/3/22
 * UserService
 **/
@Component("orderService")
public class OrderService {

    private String orderId;

    @Override
    public String toString() {
        final StringBuilder JSON = new StringBuilder("{\"OrderService\":{");
        JSON.append("\"orderId\":\"")
                .append(orderId).append('\"');
        JSON.append('}');
        JSON.append('}');
        return JSON.toString();
    }
}
