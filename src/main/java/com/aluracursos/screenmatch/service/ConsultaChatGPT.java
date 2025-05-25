package com.aluracursos.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
   public static String obtenerTraduccion(String texto) {
      String API_KEY = System.getenv("API_KEY_CHAT");
      OpenAiService service = new OpenAiService(API_KEY);
      CompletionRequest requisicion = CompletionRequest.builder()
            .model("gpt-3.5-turbo-instruct")
            .prompt("traduce a español el siguiente texto: " + texto)
            .maxTokens(1000)
            .temperature(0.7)
            .build();
      var respuesta = service.createCompletion(requisicion);
      return respuesta.getChoices().get(0).getText();
   }
}
