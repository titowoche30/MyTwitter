package twitter;
import java.util.Vector;


public abstract class Perfil {
    private String usuario;
    private Vector<String> seguidores;
    private Vector<Tweet> timeline ;
    private boolean ativo;
    
    public Perfil(String usuario){
        this.usuario = usuario;
        this.seguidores = new Vector<>();
        this.timeline = new Vector<>();
        this.ativo = true;
    }
    
    public void addSeguidor(String seguidor){
        seguidores.add(seguidor);
       
    }
    
    public void addTweet(Tweet tweet){
        timeline.add(tweet);
        
    }
   

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Vector<String> getSeguidores() {
        return seguidores;
    }

    public Vector<Tweet> getTimeline() {
        return timeline;
    }
    
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
