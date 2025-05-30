package com.aluracursos.screenmatch.model;
// si utilizara openai, api
// import com.aluracursos.screenmatch.service.ConsultaChatGPT;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long Id;
   @Column(unique = true)
   private String titulo;
   private Integer totalTemporadas;
   private String evaluacion;
   private String poster;
   @Enumerated(EnumType.STRING)
   private Categoria genero;
   private String actores;
   private String sinopsis;
   //@Transient   //hay una lista de episodios, no la uso todavia, fetch= tipo perezosa o ansiosa
   @OneToMany(mappedBy = "serie", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
   // uno o varios episodiso pueden tener solo una serie
   private List<Episodio> episodios;

   public Serie(){
      // constructor de la clase Serie, que es predeterminado
      // es exigencia de JPA, que se tenga un constructor en cada clase utilizada
   }
   public Serie(DatosSerie ds) {
      this.titulo = ds.titulo();
      this.totalTemporadas = ds.totalTemporadas();
      this.evaluacion = String.valueOf(OptionalDouble.of(Double.valueOf(ds.evaluacion())).orElse(0));
      this.poster = ds.poster();
      this.genero = Categoria.fromString(ds.genero().split(",")[0].trim());  // 1er elem de 3 elementos separados ","
      this.actores = ds.actores();
      this.sinopsis = ds.sinopsis();
      // this.sinopsis = ConsultaChatGPT.obtenerTraduccion(ds.sinopsis());
   }
   //getters and setters

   public List<Episodio> getEpisodios() {
      return episodios;
   }

   public void setEpisodios(List<Episodio> episodios) {
      episodios.forEach(e->e.setSerie(this));
      this.episodios = episodios;
   }

   public long getId() {
      return Id;
   }

   public void setId(long id) {
      Id = id;
   }

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
            "Titulo='" + titulo + '\'' + "\n" +
            "TotalTemporadas=" + totalTemporadas + "\n" +
            "Evaluacion='" + evaluacion + '\'' + "\n" +
            "Poster='" + poster + '\'' + "\n" +
            "Genero=" + genero + "\n" +
            "Actores='" + actores + '\'' + "\n" +
            "Sinopsis='" + sinopsis + '\'' + "\n" +
            "Episodios='" + episodios+'\n';
   }

}
