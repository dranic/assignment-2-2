package eu.easyrpa.course.tasks;

import eu.easyrpa.course.entity.File;
import eu.easyrpa.course.repository.FileRepository;
import eu.ibagroup.easyrpa.engine.annotation.AfterInit;
import eu.ibagroup.easyrpa.engine.annotation.ApTaskEntry;
import eu.ibagroup.easyrpa.engine.annotation.Configuration;
import eu.ibagroup.easyrpa.engine.apflow.ApTask;
import eu.ibagroup.easyrpa.engine.service.VaultService;
import eu.ibagroup.easyrpa.utils.storage.S3Manager;
import eu.ibagroup.easyrpa.utils.storage.StorageManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApTaskEntry(name = "Save Target Files Into Data Store", description = "Assignment 2.2 Step 2")
public class SaveTargetFilesIntoDataStore extends ApTask {
    @Configuration(value = "zip.file")
    private String zipFileName;
    @Configuration(value = "s3.bucket")
    private String s3Bucket;
    @Configuration(value = "s3.folder")
    private String s3Folder;
    private String zipFilePassword;
    @Inject
    private StorageManager storageManager;

    @Inject
    private FileRepository fileRepository;

    @Override
    public void execute() throws IOException {
        List<String> paths = getFiles();
        List<File> files = new ArrayList<>();
        for(String path : paths){
            files.add(readFile(path));
        }
        fileRepository.saveFiles(files);
    }

    private List<String> getFiles() {
        List<String> filesPathList = storageManager.listFiles(s3Bucket, s3Folder, ".*\\d\\.txt");
        return filesPathList;
    }

    private File readFile(String s3FilePath) throws IOException {
        InputStream fileContentInputStream = storageManager.getFile(s3Bucket, s3FilePath);
        String fileContent = IOUtils.toString(fileContentInputStream, StandardCharsets.UTF_8);
        return new File(FilenameUtils.getName(s3FilePath),fileContent);
    }

}
