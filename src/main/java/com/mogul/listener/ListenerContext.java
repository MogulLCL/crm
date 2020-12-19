package com.mogul.listener;

import com.mogul.pojo.DicValue;
import com.mogul.service.DicService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListenerContext implements ServletContextListener {
    DicService dicService =null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ApplicationContext webApplicationContext= (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        dicService = (DicService) webApplicationContext.getBean("dicService");
        Map<String, List<DicValue>> dic=dicService.getAll();
        Set<String> set= dic.keySet();
        for(String sets:set){
            System.out.println(sets+"--------"+dic.get(sets));
            servletContext.setAttribute(sets,dic.get(sets));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
