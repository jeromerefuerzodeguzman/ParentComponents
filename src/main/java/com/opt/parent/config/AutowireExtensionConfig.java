package com.opt.parent.config;

import com.opt.parent.business.AutowireExtensionFieldCallback;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class AutowireExtensionConfig implements BeanPostProcessor {

    private static final String EXTENSION_FILE = "extension.properties";

    private HashMap<String, String> extensions = new HashMap<String, String>();
    private ConfigurableListableBeanFactory configurableBeanFactory;

    @Autowired
    public AutowireExtensionConfig(ConfigurableListableBeanFactory beanFactory) {
        this.configurableBeanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.FieldCallback fieldCallback =
                new AutowireExtensionFieldCallback(configurableBeanFactory, bean, extensions);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);
        return bean;
    }

    @PostConstruct
    private void loadExtensionFile() {
        Properties prop = new Properties();
        String projectDir= System.getProperty("user.dir") + "\\extensions";

        System.out.println(projectDir + "\\" + EXTENSION_FILE);

        try {
            prop.load(new FileInputStream(new File(projectDir , EXTENSION_FILE)));
            for (Map.Entry<Object,Object> entry : prop.entrySet()) {
                extensions.put((String) entry.getKey(), (String) entry.getValue());
            }
            System.err.println(extensions);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find extension file on directory: \"" + projectDir + "\" " +
                    "extension file name: " + EXTENSION_FILE);
        }

    }
}
