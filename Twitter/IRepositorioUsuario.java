package twitter;
import Excecoes.UJCException;
import Excecoes.UNCException;
import java.util.Vector;

public interface IRepositorioUsuario {
    public void cadastrar(Perfil usuario) throws UJCException;
    public Perfil buscar(String usuario);
    public void atualizar(Perfil usuario) throws UNCException;
    public Vector<Perfil> getVetordePerfil();
}