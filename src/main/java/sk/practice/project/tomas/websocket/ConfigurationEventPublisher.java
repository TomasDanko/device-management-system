package sk.practice.project.tomas.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public ConfigurationEventPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyConfigChanged(Long deviceId) {
        messagingTemplate.convertAndSend(
                "/topic/config-changed",
                "Configuration changed for device" + deviceId
        );
    }
}
