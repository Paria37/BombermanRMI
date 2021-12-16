public class Jugador{
    private int estado;
    private String nombre;
    private int id;
    private Posicion posicion;

    public Jugador(int estado, String nombre, int id, Posicion posicion){
        this.estado = estado;
        this.nombre = nombre;
        this.id = id;
        this.posicion = posicion;
    }

    public String getNombre(){
        return this.nombre;
    }
    public Posicion getPosicion(){
        return this.posicion;
    }
    public int getEstado(){
        return this.estado;
    }
    public int getId(){
        return this.id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setPosicion(Posicion posicion){
        this.posicion = posicion;
    }
    public void setEstado(int estado){
        this.estado = estado;
    }
}
