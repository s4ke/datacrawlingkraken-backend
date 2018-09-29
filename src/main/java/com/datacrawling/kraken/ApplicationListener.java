package com.datacrawling.kraken;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author Martin Braun
 * @version 1.1.0
 * @since 1.1.0
 */
public class ApplicationListener implements org.springframework.context.ApplicationListener {

	public static final List<String> endPoints = new ArrayList<>();

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if ( event instanceof ContextRefreshedEvent ) {
			endPoints.clear();
			ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
			applicationContext.getBean( RequestMappingHandlerMapping.class ).getHandlerMethods().forEach(
					(requestMappingInfo, handlerMethod) ->
							endPoints.add(
									"(" + requestMappingInfo.toString() + ", " + handlerMethod.toString() + ")" )
			);
		}
	}

}
