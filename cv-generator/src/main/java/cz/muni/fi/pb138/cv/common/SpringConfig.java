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
        return new UserServiceImpl();
    }

    @Bean
    public CvService cvService() {
        return new CvServiceImpl();
    }

    @Bean
    public LoginsUtil loginsUtil() {
        return new LoginsUtilImpl(userService());
    }

    @Bean
    public CvUtil cvUtil(){
        return new CvUtilImpl();        
    }
}
