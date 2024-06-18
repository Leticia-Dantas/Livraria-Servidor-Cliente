package biblioteca;

public class Livros {

		  private String titulo;
		  private String autor;
		  private String genero;
		  private int exemplares;
		 
		    public Livros(String titulo, String autor, String genero, int exemplares) {
		        this.titulo = titulo;
		        this.autor = autor;
		        this.genero = genero;
		        this.exemplares = exemplares;
		    }

		    //getters
		    public String getAutor() {
		        return autor;
		    }

		    public String getGenero() {
		        return genero;
		    }
		    public String getTitulo() {
		        return titulo;
		    }

		    public int getExemplares() {
		        return exemplares;
		    }

		    // setters
		    public void setAutor(String autor) {
		        this.autor = autor;
		    }

		    public void setTitulo(String titulo) {
		        this.titulo = titulo;
		    }
		    public void setExemplares(int exemplares) {
		        this.exemplares = exemplares;
		    }
		    public void setGenero(String genero) {
		        this.genero = genero;
		    }

	
		    @Override
		    public String toString() {
		        return "Livro{" +
		                "autor='" + titulo + '\'' +
		                ", nome='" + autor + '\'' +
		                ", genero='" + genero + '\'' +
		                ", numExemplares=" + exemplares +
		                '}';
		    }
		    
}
