package com.miroslav.orarend.serviceImpl;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ThinkingConfig;
import com.miroslav.orarend.service.OllamaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OllamaServiceImpl implements OllamaService {

    private final ObjectProvider<Client> genaiProvider;

    public OllamaServiceImpl(ObjectProvider<Client> genaiProvider) {
        this.genaiProvider = genaiProvider;
    }

    @Value("${gemini.model:gemini-2.5-flash}")
    private String model;

    @Value("${gemini.maxOutputTokens:512}")
    private int maxOutputTokens;

    @Value("${gemini.temperature:0.3}")
    private float temperature;

    @Override
    public String summarizeText(String text) {
        if (text == null || text.isBlank()) return "⚠️ Nincs szöveg az összefoglaláshoz.";

        Client genai = genaiProvider.getIfAvailable();
        if (genai == null) {
            // Teszt alatt vagy gemini.enabled=false esetén:
            log.warn("Gemini kliens nem elérhető (valószínűleg gemini.enabled=false vagy nincs API kulcs).");
            return "ℹ️ AI jelenleg kikapcsolva (teszt/fejlesztői mód).";
        }

        try {
            String prompt = "Foglaljad össze röviden, magyarul, felsorolásban:\n\n" + text;

            GenerateContentConfig cfg = GenerateContentConfig.builder()
                    .candidateCount(1)
                    .maxOutputTokens(maxOutputTokens)
                    .temperature(temperature)
                    .thinkingConfig(ThinkingConfig.builder().thinkingBudget(0))
                    .build();

            GenerateContentResponse res = genai.models.generateContent(model, prompt, cfg);
            String out = res.text();
            if (out == null || out.isBlank()) return "⚠️ Üres válasz érkezett az AI-tól.";

            out = out.replaceAll("\\s+\\n", "\n").replaceAll("\\n{3,}", "\n\n").trim();
            if (out.length() > 2000) out = out.substring(0, 2000) + "…";
            return out;

        } catch (Exception e) {
            log.error("❌ Gemini hívás hiba", e);
            return "⚠️ Hiba történt az összefoglalás során: " + e.getMessage();
        }
    }
}
