//package sk.practice.project.tomas.dataload;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import sk.practice.project.tomas.entity.ComplianceReport;
//import sk.practice.project.tomas.entity.Configuration;
//import sk.practice.project.tomas.entity.Device;
//import sk.practice.project.tomas.entity.Vendor;
//import sk.practice.project.tomas.repository.ComplianceReportRepository;
//import sk.practice.project.tomas.repository.ConfigurationRepository;
//import sk.practice.project.tomas.repository.DeviceRepository;
//
//import java.util.List;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final DeviceRepository deviceRepository;
//    private final ConfigurationRepository configurationRepository;
//    private final ComplianceReportRepository complianceReportRepository;
//
//    public DataLoader(DeviceRepository deviceRepository, ConfigurationRepository configurationRepository, ComplianceReportRepository complianceReportRepository) {
//        this.deviceRepository = deviceRepository;
//        this.configurationRepository = configurationRepository;
//        this.complianceReportRepository = complianceReportRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        // Create devices
//        Device device1 = new Device("Router-1", "192.168.1.1", Vendor.CISCO);
//        Device device2 = new Device("Router-2", "192.168.1.2", Vendor.HP);
//
//        deviceRepository.saveAll(List.of(device1, device2));
//
//        // Create and add configurations
//        Configuration configuration1 = new Configuration(device1, "hostname Router-1", "checksum1");
//        Configuration configuration2 = new Configuration(device2, "hostname Router-2", "checksum2");
//
//        device1.addConfiguration(configuration1);
//        device2.addConfiguration(configuration2);
//
//        deviceRepository.saveAll(List.of(device1, device2));
//
//        // Create compliance report
//        ComplianceReport report1 = new ComplianceReport(device1, true, "All compliance checks passed");
//        ComplianceReport report2 = new ComplianceReport(device2, false, "System error");
//
//        complianceReportRepository.saveAll(List.of(report1, report2));
//
//        System.out.println("Test data inserted successfully");
//
//    }
//}
