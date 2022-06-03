package eu.easyrpa.course.tasks;

import eu.easyrpa.course.entity.File;
import eu.easyrpa.course.repository.FileRepository;
import eu.ibagroup.easyrpa.engine.annotation.AfterInit;
import eu.ibagroup.easyrpa.engine.annotation.ApTaskEntry;
import eu.ibagroup.easyrpa.engine.annotation.Configuration;
import eu.ibagroup.easyrpa.engine.apflow.ApTask;
import eu.ibagroup.easyrpa.engine.service.NotificationService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ApTaskEntry(name = "Send Result Notification", description = "Assignment 2.2 Step 3")
public class SendResultNotification extends ApTask {
    @Configuration(value = "notification.channel")
    private String notificationChannel;
    @Configuration(value="notification.template")
    private String notificationTemplate;

    @Inject
    private NotificationService notificationService;

    @Inject
    private FileRepository fileRepository;

    @Override
    public void execute() {
        List<File> files = fileRepository.findAll();
        String content = "";
        for (File file : files) {
            content += file.getFileContent();
            fileRepository.delete(file);
        }
        Map data = new HashMap();
        data.put("content",content);
        notificationService.evaluateTemplateAndSend(notificationTemplate,data,notificationChannel,new ArrayList<>());
    }

}
