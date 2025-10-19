package com.miroslav.orarend.service;

/**
 * Az Ollama modellel (pl. DeepSeek-R1) való kommunikációt végzi.
 */
public interface OllamaService {

    /**
     * Egy megadott szöveget elküld az Ollama-nak összefoglalásra.
     * @param text a szöveg, amit összefoglalunk
     * @return az AI által generált összefoglalás
     */
    String summarizeText(String text);
}
