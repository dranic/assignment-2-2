package eu.easyrpa.course.ap;

import eu.easyrpa.course.tasks.SendResultNotification;
import eu.easyrpa.course.tasks.UploadZipContentToS3;
import eu.ibagroup.easyrpa.engine.annotation.ApModuleEntry;
import eu.ibagroup.easyrpa.engine.apflow.ApModule;
import eu.ibagroup.easyrpa.engine.apflow.TaskOutput;
import eu.ibagroup.easyrpa.engine.boot.ApModuleRunner;
import eu.easyrpa.course.tasks.SaveTargetFilesIntoDataStore;
import eu.ibagroup.easyrpa.engine.boot.configuration.DevelopmentConfigurationModule;
import eu.ibagroup.easyrpa.engine.boot.configuration.StandaloneConfigurationModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApModuleEntry(name = "Assignment 2.2", description = "Using Data Services")
public class Module extends ApModule {

    public TaskOutput run() throws Exception {
        execute(getInput(), UploadZipContentToS3.class).get();
        execute(getInput(), SaveTargetFilesIntoDataStore.class).get();
        return execute(getInput(), SendResultNotification.class).get();
    }

    public static void main(String[] arg) {
        ApModuleRunner runner = new ApModuleRunner();
        //use DevelopmentConfigurationModule - to run on remote Control Server
        //use StandaloneConfigurationModule - to run locally (not all Data Services are emulated locally)
        runner.localLaunch(Module.class, new DevelopmentConfigurationModule());
    }
}