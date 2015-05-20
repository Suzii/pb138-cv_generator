/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.common;

import cz.muni.fi.pb138.cv.servlets.utils.LoginsUtilImpl;
import cz.muni.fi.pb138.cv.servlets.utils.LoginsUtil;
import cz.muni.fi.pb138.cv.servlets.utils.CvUtilImpl;
import cz.muni.fi.pb138.cv.servlets.utils.CvUtil;
import cz.muni.fi.pb138.cv.service.*;
import cz.muni.fi.pb138.cv.servlets.*;
import cz.muni.fi.pb138.cv.servlets.utils.SessionService;
import cz.muni.fi.pb138.cv.servlets.utils.SessionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author Zuzana
 */
@Configuration
@PropertySource(value = {"classpath:cvgenerator.properties"})
public class SpringConfig {

    @Autowired
    private Environment environment;

    @Bean
    public UserService userService() {
        //return new MockedUserServiceImpl(); //for testing only
        //return new UserServiceImpl(getDbFolder());
        return new UserServiceImpl();
    }

    @Bean
    public CvService cvService() {
        //return new MockedCvServiceImpl(); // for testing only
        //return new CvServiceImpl(getDbFolder(), getLatexFolder());
        return new CvServiceImpl();
    }

    @Bean
    public LoginsUtil loginsUtil() {
        return new LoginsUtilImpl(userService());
    }

    @Bean
    public CvUtil cvUtil() {
        return new CvUtilImpl();
    }

    @Bean
    public SessionService sessionService() {
        return new SessionServiceImpl();
    }

    private String getDbFolder() {
        return environment.getRequiredProperty("database.folder");
    }

    private String getLatexFolder() {
        return environment.getRequiredProperty("latex.folder");
    }
}
