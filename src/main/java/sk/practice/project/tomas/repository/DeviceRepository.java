package sk.practice.project.tomas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.practice.project.tomas.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
