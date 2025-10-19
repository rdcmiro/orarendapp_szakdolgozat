package com.miroslav.orarend.repository;

import com.miroslav.orarend.pojo.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
