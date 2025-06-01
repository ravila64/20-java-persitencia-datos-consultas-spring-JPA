package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// con que entidad se trabja en el JpaRepository(Serie, long)
public interface SerieRepository extends JpaRepository<Serie, Long> {
   // busqueda x Titulo Conteniendo parte titulo e ignorando mayus-minus
   Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

   // Buscar Top 5 mejores series x evaluacion, orden de Mayor a menor
   List<Serie> findTop5ByOrderByEvaluacionDesc();

   // Lista series por categoria
   List<Serie> findByGenero(Categoria categoria);

   @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
   List<Serie> seriesPorTemparadaYEvaluacion(int totalTemporadas, Double evaluacion);

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.titulo ILIKE %:nombreEpisodio%")
   List<Episodio> episodiosPorNombre(String nombreEpisodio);

   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie" +
         " ORDER BY e.evaluacion DESC LIMIT 5")
   List<Episodio> top5EpisodiosPorSerie(Serie serie);


   //Optional<Serie> findByGenero(Categoria categoria); // probe y no funciono cuando no existe categoria
   // busca una cantidad de temporadas >= #dado y una evaluacion>=numero dado, en resumen filtrar series
   //List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);
   // qUEIRES NATIVAS
   //@Query(value="SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion >=7.5", nativeQuery=true)
   // JPAL

}
