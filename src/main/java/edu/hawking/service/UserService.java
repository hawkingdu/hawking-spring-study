package edu.hawking.service;

import edu.hawking.framework.Autowired;
import edu.hawking.framework.Component;

/**
 * 杜皓君 created by 2021/3/22
 * UserService
 **/
@Component("userService")
public class UserService {

    private String userId;

    @Autowired
    OrderService orderService;

    public void test() {
        System.out.println(orderService.toString());
    }
    @Override
    public String toString() {
        final StringBuilder JSON = new StringBuilder("{\"UserService\":{");
        JSON.append("\"userId\":\"")
                .append(userId).append('\"');
        JSON.append(",\"orderService\":")
                .append(orderService);
        JSON.append('}');
        JSON.append('}');
        return JSON.toString();
    }
}
