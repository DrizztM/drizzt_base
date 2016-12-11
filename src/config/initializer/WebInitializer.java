package config.initializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.IntrospectorCleanupListener;

import config.ApplicationConfig;
import config.WebConfig;

/**
 * 
 * AdminInitializer Servlet3.0 工程入口类
 *
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(IntrospectorCleanupListener.class);
        super.onStartup(servletContext);
    }


    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    protected String getServletName() {
        return this.getClass().getSimpleName();
    }

    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { ApplicationConfig.class };
    }

    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter };
    }
}
