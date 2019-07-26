package twitter;
import Excecoes.MFPException;
import Excecoes.PDException;
import Excecoes.PEException;
import Excecoes.PIException;
import Excecoes.SIException;
import Excecoes.SRException;
import java.util.Vector;


public interface ITwitter {
    public void criarPerfil(Perfil usuario)throws PEException;
    public void cancelarPerfil(String usuario)throws PDException,PIException ;
    public void tweetar(String usuario,String Mensagem)throws PIException,PDException,MFPException;
    public Vector <Tweet> timeline(String usuario)throws PIException,PDException;
    public Vector <Tweet> tweets(String usuario);
    public void seguir(String seguidor,String seguido)throws PIException,PDException,SIException,SRException;
    public int numeroSeguidores(String usuario) ;
    public Vector <Perfil> seguidores(String usuario)throws PIException,PDException;
}
