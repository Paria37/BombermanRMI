import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;




public class BombermanServer implements Bomberman{

    private ArrayList<Jugador> jugadores;
    private int[][] mapa;

    public BombermanServer() {
        jugadores = new ArrayList<Jugador>();
        mapa = new int[20][20];
    }

    public void nuevaPartida(int N) throws RemoteException{

        for(int i=0; i<N; i++){
            jugadores.add(new Jugador(0,"nombre",i,new Posicion(i,i)));
        }
        
    }

    public boolean nuevoJugador(String nombre) throws RemoteException{
        boolean flag = true;
        int cont = 0;
        while(flag && cont < jugadores.size()){
            if (jugadores.get(cont).getNombre().equals("nombre")){
                jugadores.get(cont).setNombre(nombre);
                jugadores.get(cont).setEstado(1);
                flag = false;
            }else{
                cont ++;
            }
        }
        if(cont>= jugadores.size()){
            return false;
        }
        else{
            switch (cont) {
                case 0:
                    jugadores.get(cont).getPosicion().setX(0);
                    jugadores.get(cont).getPosicion().setY(0);
                    break;
                case 1:
                    jugadores.get(cont).getPosicion().setX(0);
                    jugadores.get(cont).getPosicion().setY(19);
                    break;
                case 2:
                    jugadores.get(cont).getPosicion().setX(19);
                    jugadores.get(cont).getPosicion().setY(0);
                    break;
                case 3:
                    jugadores.get(cont).getPosicion().setX(19);
                    jugadores.get(cont).getPosicion().setY(19);
                    break;
            
                default:
                    break;
            }

            return true;
        }
     
    }
    public boolean partidaLista() throws RemoteException{
        boolean flag = true;
        int cont = 0;

        while(flag && cont < jugadores.size()){
            if(jugadores.get(cont).getEstado() == 0){
                flag = false;
            }
            cont ++;
        }

        return flag;

    }
    public void movimiento(int id, Posicion posicion) throws RemoteException{
        //verificar movimientos valido (paredes)
        jugadores.get(id).setPosicion(posicion);
        //verificar explosiones
        //cambiar estado
    }
    public BombermanServer obtenerEstado() throws RemoteException{
        BombermanServer aux = new BombermanServer();
        aux.setJugadores(jugadores);
        aux.setMapa(mapa);
        return aux;
    }
    public void setMapa(int mapa[][]){
        this.mapa = mapa;
    }
    public void setJugadores(ArrayList<Jugador> jugadores){
        this.jugadores = jugadores;
    }
    public static void main(String args[]) {
        try {
            BombermanServer obj = new BombermanServer();
            Bomberman stub = (Bomberman) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Bomberman", stub);
            System.err.println("BombermanServer listo!");
        } catch (Exception e) {
            System.err.println("BombermanServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}