package com.derzhavets.configuration;

import com.derzhavets.controller.model.Overtime;
import com.derzhavets.repository.entity.OvertimeEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class OvertimesServiceConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        mapper.addMappings(new PropertyMap<OvertimeEntity, Overtime>() {
            protected void configure() {
                map().setEmployeeId(source.getEmployee().getId());
                map().setProjectId(source.getProject().getId());
            }
        });

        return mapper;
    }
}
