package com.aluracursos.screenmatch.enums;

public class MainPlanetas {
   public static void main(String[] args) {
      // Recorrer todos los planetas del Enum
      for (Planetas planeta : Planetas.values()) {
         System.out.println(planeta);
      }
   }
}
