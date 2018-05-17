package com.opt.parent.business;

import com.opt.parent.domain.AutowireExtension;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import java.lang.reflect.Field;
import java.util.HashMap;

public class AutowireExtensionFieldCallback implements FieldCallback {

    private HashMap<String, String> extensions = new HashMap<String, String>();
    private ConfigurableListableBeanFactory configurableBeanFactory;
    private Object bean;

    public AutowireExtensionFieldCallback(ConfigurableListableBeanFactory bf,
                                          Object bean,
                                          HashMap<String,String> ex) {
        extensions = ex;
        configurableBeanFactory = bf;
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

        if (!field.isAnnotationPresent(AutowireExtension.class)) {
            return;
        }

        ReflectionUtils.makeAccessible(field);

        String extensionId = extensions.get(field.getType().getSimpleName());

        Object annotatedObj = configurableBeanFactory.getBean(extensionId);

        field.set(bean, annotatedObj);
    }
}
