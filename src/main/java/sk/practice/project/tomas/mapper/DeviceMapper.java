package sk.practice.project.tomas.mapper;

import sk.practice.project.tomas.dto.DeviceDto;
import sk.practice.project.tomas.entity.Device;

public class DeviceMapper {

    public static DeviceDto toDto(Device device) {
        if (device == null) {
            return null;
        } else {
            return new DeviceDto(
                    device.getId(),
                    device.getHostname(),
                    device.getIpAddress(),
                    device.getVendor(),
                    device.getCreatedAt()
            );
        }
    }

    public static Device toEntity(DeviceDto dto) {
        if (dto == null) {
            return null;
        } else {
            return new Device(
                    dto.getHostname(),
                    dto.getIpAddress(),
                    dto.getVendor()
            );
        }
    }

    public static void updateEntity(Device device, DeviceDto deviceDto) {
        device.setHostname(deviceDto.getHostname());
        device.setIpAddress(deviceDto.getIpAddress());
        device.setVendor(deviceDto.getVendor());
    }
}
