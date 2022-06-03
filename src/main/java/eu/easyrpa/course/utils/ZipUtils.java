package eu.easyrpa.course.utils;

import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class ZipUtils {

    public static Collection<File> extractFiles(String zipFileNameInResources, String zipFilePassword) throws IOException {
        Objects.requireNonNull(zipFilePassword, "ZIP file password shouldn't be null");
        File file = new File(ZipUtils.class.getClassLoader().getResource("").getPath() + "/extracted_zip/");
        FileUtils.forceMkdir(file);
        FileUtils.cleanDirectory(file);
        new ZipFile(ZipUtils.class.getClassLoader().getResource(zipFileNameInResources).getPath(), zipFilePassword.toCharArray()).extractAll(file.getAbsolutePath());
        return FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
    }
}
