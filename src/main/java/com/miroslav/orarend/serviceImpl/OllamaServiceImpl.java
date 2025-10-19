package com.miroslav.orarend.serviceImpl;

import com.miroslav.orarend.service.OllamaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaServiceImpl implements OllamaService {

    private final RestTemplate restTemplate;

    @Value("${ollama.url:http://localhost:11434/api/generate}")
    private String ollamaUrl;

    @Override
    public String summarizeText(String text) {
        if (text == null || text.isBlank()) {
            return "⚠️ Nincs szöveg az összefoglaláshoz.";
        }

        String prompt = "Foglaljad össze röviden, magyarul:\n\n" + text;
        Map<String, Object> body = Map.of(
                "model", "deepseek-r1",
                "prompt", prompt
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<byte[]> resp = restTemplate.exchange(
                    ollamaUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    byte[].class
            );

            if (resp.getBody() == null) return "⚠️ Üres válasz érkezett az AI-tól.";

            // 🔹 kényszerített UTF-8 dekódolás
            String raw = new String(resp.getBody(), StandardCharsets.UTF_8);

            // 🔹 "response" mezők összefűzése, ha streames JSON jött
            Pattern p = Pattern.compile("\"response\":\"(.*?)\"");
            Matcher m = p.matcher(raw);

            StringBuilder result = new StringBuilder();
            while (m.find()) {
                result.append(m.group(1)
                        .replace("\\n", "\n")
                        .replace("\\\"", "\""));
            }

            String summary = !result.isEmpty() ? result.toString() : raw;

            // 🔹 formázás
            summary = summary
                    .replaceAll("\\s+", " ")
                    .replaceAll("([.!?])", "$1 ")
                    .replaceAll(" +([,.])", "$1")
                    .trim();

            if (summary.length() > 1000)
                summary = summary.substring(0, 1000) + "…";

            return summary;

        } catch (Exception e) {
            log.error("❌ Hiba az Ollama API hívásakor", e);
            return "⚠️ Hiba történt az összefoglalás során: " + e.getMessage();
        }
    }
}
