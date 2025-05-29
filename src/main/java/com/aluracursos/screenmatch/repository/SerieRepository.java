package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

// con que entidad se trabja en el JpaRepository(Serie, long)
public interface SerieRepository extends JpaRepository<Serie, Long> {

}
