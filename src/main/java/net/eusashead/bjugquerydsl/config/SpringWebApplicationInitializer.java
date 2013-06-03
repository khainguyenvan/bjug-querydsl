package net.eusashead.bjugquerydsl.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class SpringWebApplicationInitializer implements WebApplicationInitializer {

    @Override 
    public void onStartup(ServletContext ctx) throws ServletException {

    	// Set up the app ctx
        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(WebConfig.class);
        
        // Default profile is dev
        rootCtx.getEnvironment().setActiveProfiles("dev");
        
        // Set the listener
        ctx.addListener(new ContextLoaderListener(rootCtx));
      
        // Register the Spring Dispatcher servlet
		ServletRegistration.Dynamic dispatcher = ctx.addServlet(
				"dispatcher", new DispatcherServlet(rootCtx));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/app/*");
        
    }

}