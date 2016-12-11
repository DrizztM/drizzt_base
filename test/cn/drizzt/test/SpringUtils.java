package cn.drizzt.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringUtils {
	private static ApplicationContext ctx;

	public static ApplicationContext getApplicationContext() {
		ctx = new AnnotationConfigApplicationContext("config");
		return ctx;
	}
}
