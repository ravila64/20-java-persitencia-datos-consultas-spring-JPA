package com.aluracursos.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
   private String titulo;
   private Integer totalTemporadas;
   private String evaluacion;
   private String poster;
   private Categoria genero;
   private String actores;
   private String sinopsis;

   public Serie(DatosSerie ds) {
      this.titulo = ds.titulo();
      this.totalTemporadas = ds.totalTemporadas();
      this.evaluacion = String.valueOf(OptionalDouble.of(Double.valueOf(ds.evaluacion())).orElse(0));
      this.poster = ds.poster();
      this.genero = Categoria.fromString(ds.genero().split(",")[0].trim());  // 1er elem de 3 elementos separados ","
      this.actores = ds.actores();
      this.sinopsis = ds.sinopsis();
   }
   //getters and setters

   public String getActores() {
      return actores;
   }

   public void setActores(String actores) {
      this.actores = actores;
   }

   public String getEvaluacion() {
      return evaluacion;
   }

   public void setEvaluacion(String evaluacion) {
      this.evaluacion = evaluacion;
   }

   public Categoria getGenero() {
      return genero;
   }

   public void setGenero(Categoria genero) {
      this.genero = genero;
   }

   public String getPoster() {
      return poster;
   }

   public void setPoster(String poster) {
      this.poster = poster;
   }

   public String getSinopsis() {
      return sinopsis;
   }

   public void setSinopsis(String sinopsis) {
      this.sinopsis = sinopsis;
   }

   public String getTitulo() {
      return titulo;
   }

   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }

   public Integer getTotalTemporadas() {
      return totalTemporadas;
   }

   public void setTotalTemporadas(Integer totalTemporadas) {
      this.totalTemporadas = totalTemporadas;
   }
   // to String()
   @Override
   public String toString() {
      return "Serie:" + "\n" +
            "Titulo='" + titulo + '\''+ "\n" +
            "TotalTemporadas=" + totalTemporadas + "\n" +
            "Evaluacion='" + evaluacion + '\'' + "\n" +
            "Poster='" + poster + '\'' +"\n" +
            "Genero=" + genero +"\n" +
            "Actores='" + actores + '\'' +"\n" +
            "Sinopsis='" + sinopsis + '\'';
   }

}
