package com.yunque.system;

import com.yunque.common.security.annotation.EnableCustomConfig;
import com.yunque.common.security.annotation.EnableResourceServer;
import com.yunque.common.security.annotation.EnableRyFeignClients;
import com.yunque.common.swagger.annotation.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块
 *
 * @author xueyi
 */
@EnableCustomConfig
@EnableCustomSwagger
@EnableResourceServer
@EnableRyFeignClients
@SpringBootApplication
public class YunQueSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(YunQueSystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  _____     __   ____     __        \n" +
                "  \\   _\\   /  /  \\   \\   /  /   \n" +
                "  .-./ ). /  '    \\  _. /  '       \n" +
                "  \\ '_ .') .'      _( )_ .'        \n" +
                " (_ (_) _) '   ___(_ o _)'          \n" +
                "   /    \\   \\ |   |(_,_)'         \n" +
                "   `-'`-'    \\|   `-'  /           \n" +
                "  /  /   \\    \\\\      /          \n" +
                " '--'     '----'`-..-'              ");
    }
}