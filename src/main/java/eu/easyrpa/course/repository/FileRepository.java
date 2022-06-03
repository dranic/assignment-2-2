package eu.easyrpa.course.repository;

import eu.easyrpa.course.entity.File;
import eu.ibagroup.easyrpa.persistence.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface FileRepository extends CrudRepository<File, String> {

    default List<File> getFilesByName(String name) {
        List<File> result = new ArrayList<>();
        for (File file : findAll()) {
            if (file.getFileName().equals(name)) {
                result.add(file);
            }
        }
        return result;
    }

    default void saveFiles(List<File> files) {
        for (File file : files) {
            save(file);
        }
    }
}
