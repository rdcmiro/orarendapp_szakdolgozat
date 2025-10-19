package com.miroslav.orarend.service;

import com.miroslav.orarend.pojo.FileEntity;
import com.miroslav.orarend.pojo.User;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileService {

    /**
     * Fájlt ment a rendszerbe és visszaadja a mentett FileEntity-t.
     */
    FileEntity saveFile(MultipartFile file, User user, Long lessonId) throws IOException;

    /**
     * Egy fájl tartalmát (szövegként) kinyeri.
     * PDF, DOCX vagy TXT formátumokat kezel.
     */
    String extractTextFromFile(Long fileId) throws Exception;

    /**
     * AI-alapú összefoglalót készít a fájl tartalmából.
     */
}
