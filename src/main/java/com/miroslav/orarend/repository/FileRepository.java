package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.FileEntity;
import com.miroslav.orarend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAllByUser(User user);
}
