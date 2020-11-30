package com.unriviel.api.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {

    @Bean
    ModelMapper getmapper(){
        return new ModelMapper();
    }
}
