package eu.easyrpa.course.tasks;

import eu.easyrpa.course.utils.ZipUtils;
import eu.ibagroup.easyrpa.engine.annotation.AfterInit;
import eu.ibagroup.easyrpa.engine.annotation.ApTaskEntry;
import eu.ibagroup.easyrpa.engine.annotation.Configuration;
import eu.ibagroup.easyrpa.engine.apflow.ApTask;
import eu.ibagroup.easyrpa.engine.service.VaultService;
import eu.ibagroup.easyrpa.utils.storage.StorageManager;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

@Slf4j
@ApTaskEntry(name = "Upload Zip Content to S3", description = "Assignment 2.2 Step 1")
public class UploadZipContentToS3 extends ApTask {

    @Configuration(value = "zip.file")
    private String zipFileName;
    @Configuration(value = "s3.bucket")
    private String s3Bucket;
    @Configuration(value = "s3.folder")
    private String s3Folder;
    private String zipFilePassword;

    @Inject
    private VaultService vaultService;
    @Inject
    private StorageManager storageManager;

    @AfterInit
    public void init() {
        zipFilePassword = vaultService.getSecret("zip_credentials",String.class);

    }

    @Override
    public void execute() throws IOException {
        Collection<File> filesFromZip = ZipUtils.extractFiles(zipFileName, zipFilePassword);
        for(File file : filesFromZip){
            uploadFile(file.getName(), Files.readAllBytes(file.toPath()));
            file.delete();
        }
    }

    private void uploadFile(String fileName, byte[] fileContent) {
        storageManager.uploadFile(s3Bucket, s3Folder + fileName, new ByteArrayInputStream(fileContent), "text/plain", fileContent.length);
    }

}
