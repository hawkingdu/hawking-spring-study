package edu.hawking;

import edu.hawking.framework.HawkingApplicationContext;
import edu.hawking.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 杜皓君 created by 2021/3/22
 * Application
 **/
public class Application {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        HawkingApplicationContext applicationContext = new HawkingApplicationContext(AppConfig.class);

        System.out.println((UserService)applicationContext.getBean("userService"));
        System.out.println((UserService)applicationContext.getBean("userService"));
        UserService userService = (UserService)applicationContext.getBean("userService");
        userService.test();
    }
}
