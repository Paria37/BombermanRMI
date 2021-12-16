import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bomberman extends Remote{

    public void nuevaPartida(int N) throws RemoteException;

    public boolean nuevoJugador(String nombre) throws RemoteException;

    public boolean partidaLista() throws RemoteException;

    public void movimiento(int id, Posicion posicion) throws RemoteException;

    public BombermanServer obtenerEstado() throws RemoteException;

}