package sk.practice.project.tomas.mapper;

import sk.practice.project.tomas.dto.ConfigurationDto;
import sk.practice.project.tomas.entity.Configuration;

public class ConfigurationMapper {

    public static ConfigurationDto toDto(Configuration configuration) {
        if (configuration == null) {
            return null;
        } else {
            return new ConfigurationDto(
                    configuration.getId(),
                    configuration.getDevice().getId(),
                    configuration.getContent(),
                    configuration.getChecksum(),
                    configuration.getCreatedAt()
            );
        }
    }
}
