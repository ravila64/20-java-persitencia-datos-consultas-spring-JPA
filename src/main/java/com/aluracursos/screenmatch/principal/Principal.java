package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import com.aluracursos.screenmatch.model.Serie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
   //private final String API_KEY = "&apikey=TU-APIKEY-OMDB";
   private Scanner teclado = new Scanner(System.in);
   private ConsumoAPI consumoApi = new ConsumoAPI();
   private final String URL_BASE = "https://www.omdbapi.com/?t=";
   private ConvierteDatos conversor = new ConvierteDatos();
   private final String API_KEY = "&apikey=" + System.getenv("API_KEY_MOVIES");
   private List<DatosSerie> datosSeries = new ArrayList<>();

   public void muestraElMenu() {
      var opcion = -1;
      while (opcion != 0) {
         var menu = """
               1 - Buscar series 
               2 - Buscar episodios
               3 - Mostrar series buscadas
               
               0 - Salir
               """;
         System.out.println(menu);
         System.out.print("Digite Opcion [1..3, 0=salir]-->");
         opcion = teclado.nextInt();
         teclado.nextLine();

         switch (opcion) {
            case 1:
               buscarSerieWeb();
               break;
            case 2:
               buscarEpisodioPorSerie();
               break;
            case 3:
               mostrarSeriesBuscadas();
               break;
            case 0:
               System.out.println("Cerrando la aplicación...");
               break;
            default:
               System.out.println("Opción inválida");
         }
      }
   }

   private DatosSerie getDatosSerie() {
      System.out.println("Escribe el nombre de la serie que deseas buscar");
      var nombreSerie = teclado.nextLine();
      var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
      System.out.println(json);
      DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
      return datos;
   }

   private void buscarEpisodioPorSerie() {
      DatosSerie datosSerie = getDatosSerie();
      List<DatosTemporadas> temporadas = new ArrayList<>();
      for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
         var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
         DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
         temporadas.add(datosTemporada);
      }
      temporadas.forEach(System.out::println);
   }

   private void buscarSerieWeb() {
      DatosSerie datos = getDatosSerie();
      datosSeries.add(datos);
      System.out.println(datos);
   }

   private void mostrarSeriesBuscadas() {
      // datosSeries.forEach(System.out::println);
      // se utilizara la clase Serie
      List<Serie> series = new ArrayList<>();
      series = datosSeries.stream()
            .map(d->new Serie(d))
            .collect(Collectors.toList());
      series.stream()
            .sorted(Comparator.comparing(Serie::getGenero))
            .forEach(System.out::println);
   }

}

