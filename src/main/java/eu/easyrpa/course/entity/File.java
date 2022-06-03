package eu.easyrpa.course.entity;

import eu.ibagroup.easyrpa.persistence.annotation.Column;
import eu.ibagroup.easyrpa.persistence.annotation.Entity;
import eu.ibagroup.easyrpa.persistence.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(value = "file_content")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class File {
    @Id
    @Column("file_name")
    private String fileName;

    @Column("file_content")
    private String fileContent;

}
