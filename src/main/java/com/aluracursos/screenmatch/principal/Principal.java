package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import com.aluracursos.screenmatch.model.Serie;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
   //private final String API_KEY = "&apikey=TU-APIKEY-OMDB";
   private Scanner teclado = new Scanner(System.in);
   private ConsumoAPI consumoApi = new ConsumoAPI();
   private final String URL_BASE = "https://www.omdbapi.com/?t=";
   private ConvierteDatos conversor = new ConvierteDatos();
   private final String API_KEY = "&apikey=" + System.getenv("API_KEY_MOVIES");
   private List<DatosSerie> datosSeries = new ArrayList<>();
   private SerieRepository repositorio;
   private List<Serie> series;

   public Principal(SerieRepository repository) {
      this.repositorio = repository;
   }

   public void muestraElMenu() {
      var opcion = -1;
      while (opcion != 0) {
         var menu = """
               1 - Buscar series 
               2 - Buscar episodios
               3 - Mostrar series buscadas
               4 - Buscar series por titulo 
               
               0 - Salir
               """;
         System.out.println(menu);
         System.out.print("Digite Opcion [1..4, 0=salir]-->");
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
            case 4:
               buscarSeriesPorTitulo();
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
      // este getDatosSerie, viene de una API, se cargara desde la base de datos
      //DatosSerie datosSerie = getDatosSerie();
      // muestra series de la base de datos
      mostrarSeriesBuscadas();
      System.out.println("Escriba nombre de serie para ver los episodios ");
      var nombreSerie = teclado.nextLine();

      Optional<Serie> serie = series.stream()
            .filter(s -> s.getTitulo().toUpperCase().trim().contains(nombreSerie.toUpperCase().trim()))
            .findFirst();
      if (serie.isPresent()) {
         var serieEncontrada = serie.get();
         List<DatosTemporadas> temporadas = new ArrayList<>();
         for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
            var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
         }
         temporadas.forEach(System.out::println);
         //obtener una lista plana de todos los episodios de todas las series, flatMap()
         List<Episodio> episodios = temporadas.stream()
               .flatMap(d -> d.episodios().stream()
                     .map(e -> new Episodio(d.numero(), e)))
               .collect(Collectors.toList());
         serieEncontrada.setEpisodios(episodios);
         repositorio.save(serieEncontrada);
      } else {
         System.out.println("Serie no esta presente...!!!");
      }
   }

   private void buscarSerieWeb() {
      DatosSerie datos = getDatosSerie();
      // reemplaza la add a la lista
      Serie serie = new Serie(datos);
      repositorio.save(serie); // hace grabacion tabla de la BD
      //datosSeries.add(datos);  // estaba antes
      System.out.println(datos);
   }

   private void mostrarSeriesBuscadas() {
      // datosSeries.forEach(System.out::println);
      // se utilizara la clase Serie
      // esta parte lo descxtivo
//      List<Serie> series = new ArrayList<>();
//      series = datosSeries.stream()
//            .map(d->new Serie(d))
//            .collect(Collectors.toList());
//    List<Serie> series = repositorio.findAll();
      // series se declara gobal
      series = repositorio.findAll();
      series.stream()
            .sorted(Comparator.comparing(Serie::getGenero))
            .forEach(System.out::println);
   }

   private void buscarSeriesPorTitulo() {
      System.out.println("Aqui voy");
   }

}

