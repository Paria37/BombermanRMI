
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;
/**
 *
 * @author angel_23
 */
public class Cliente {
    public static void main(String[] args) throws RemoteException, NotBoundException,InterruptedException {
        if(args.length>0){
            if(args[0].equals("nueva_partida")){
                //String host = args[1];
                System.out.println("Ingresa tu nombre\n");
                String nombre ="Paco putazos";
                System.out.println("Ingresa el numero maximo de jugadores\n");
                int n_max =2;                
                Marco m = new Marco(null,nombre,n_max);
                m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }else if(args[0].equals("unirse_partida")){
                System.out.println("Ingresa tu nombre\n");
                //String host = args[1];
                String nombre ="Paco madrazos";   
                Marco m = new Marco(null,nombre);
                m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
            }else{
                System.out.println("Ingrese un argumento valido: nueva_partida o unirse_partida\n");
            }
        }else{
            System.out.println("Se requiere un argumento: nueva_partida o unirse_partida\n");
        }
    }
}