package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

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
   List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);


   //Optional<Serie> findByGenero(Categoria categoria); // probe y no funciono cuando no existe categoria
   // buscar por temporadas y evaluacion, en resumen filtrar series
   //
}
