package com.miroslav.orarend.resource;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileController interface — a fájl feltöltés és AI összefoglalás REST végpontjaihoz.
 *
 * A szakdolgozathoz:
 * - tisztán elválasztja az API definíciót a megvalósítástól
 * - dokumentált, bővíthető architektúrát ad
 */
@RequestMapping("/files")
public interface FileResource {

    /**
     * Fájl feltöltése a szerverre.
     * @param file a feltöltött fájl
     * @param lessonId opcionális óra ID
     * @param authentication az aktuális felhasználó
     * @return a mentett fájl metaadatai
     */
    @PostMapping("/upload")
    ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "lessonId", required = false) Long lessonId,
            Authentication authentication
    );

    /**
     * Egy feltöltött fájl összefoglalása AI segítségével (Ollama DeepSeek-R1).
     * @param id a fájl azonosítója
     * @return az AI által generált összefoglaló
     */
    @GetMapping("/{id}/summary")
    ResponseEntity<?> summarizeFile(@PathVariable Long id);
}
