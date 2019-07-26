package twitter;

public class TrabalhoFinal {
    
    public static void main(String[] args){
     IRepositorioUsuario repositorioUsuario = RepositorioUsuario.getInstance();
     MyTwitter myTwitter = new MyTwitter(repositorioUsuario);
     Menu menu = new Menu(myTwitter); 
     menu.main();
    }
    
}
    

