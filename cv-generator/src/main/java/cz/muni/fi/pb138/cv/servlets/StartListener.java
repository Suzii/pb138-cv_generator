/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import cz.muni.fi.pb138.cv.servlets.utils.LoginsUtil;
import cz.muni.fi.pb138.cv.servlets.utils.CvUtil;
import cz.muni.fi.pb138.cv.common.SpringConfig;
import cz.muni.fi.pb138.cv.service.CvService;
import cz.muni.fi.pb138.cv.service.UserService;
import cz.muni.fi.pb138.cv.servlets.utils.SessionService;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Web application lifecycle listener.
 *
 * @author Zuzana
 */
public class StartListener implements ServletContextListener {

    final static Logger log = LoggerFactory.getLogger(StartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Web application initialized");
        ServletContext servletContext = sce.getServletContext();
        ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        servletContext.setAttribute("loginsUtil", appContext.getBean("loginsUtil", LoginsUtil.class));
        servletContext.setAttribute("cvUtil", appContext.getBean("cvUtil", CvUtil.class));
        servletContext.setAttribute("userService", appContext.getBean("userService", UserService.class));
        servletContext.setAttribute("cvService", appContext.getBean("cvService", CvService.class));
        servletContext.setAttribute("sessionService", appContext.getBean("sessionService", SessionService.class));
        log.info("managers instantiated");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Application clean-up");
    }
}
