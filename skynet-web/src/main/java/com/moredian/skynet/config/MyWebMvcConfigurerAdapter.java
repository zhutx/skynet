package com.moredian.skynet.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(MyWebMvcConfigurerAdapter.class);
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@SuppressWarnings("unused")
	private static List<String> UN_CHECK_URLS = new ArrayList<>();
	
	static { // 持有会话后，可直接放行的通用请求
		
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**")
    	    .excludePathPatterns(
    			"/", 
    			"/swagger-ui.html"); // 不拦截无需检查会话的请求
    	
    	
        super.addInterceptors(registry);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html" );
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
    
    public class SessionInterceptor extends HandlerInterceptorAdapter {
    	
    	@Override
    	public boolean preHandle(HttpServletRequest request,
    			HttpServletResponse response, Object handler) throws Exception {
    		
    		String sessionid = request.getSession().getId();
    		
    		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
    		String value = ops.get(sessionid);
    		if(value != null) {
    			logger.info("session info: " + value);
    		} 
    		
    		stringRedisTemplate.expire(sessionid, 30, TimeUnit.MINUTES);
    		
    		return true;
    		
    	}

    }
    
}
