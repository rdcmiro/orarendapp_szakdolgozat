package com.miroslav.orarend.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
@DynamicUpdate
@DynamicInsert
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String contentType;

    private String filePath;

    private LocalDateTime uploadedAt;


    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
