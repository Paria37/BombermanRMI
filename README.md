# BombermanRMI
0. Crear la carpeta bin(solo hay que crearla, no hay que moverse ahi)
1. Compilar los fuentes.
    $javac -d bin Bomberman.java BombermanServidor.java Cliente.java
2. Iniciar servicio de registro. 
    $../bin> rmiregistry
3. Iniciar el servidor. 
  $java -cp ./bin/ -Djava.rmi.server.codebase=file:./bin/ BombermanServidor

Cuando vuelvas a compilar y quieras ejecutar de nuevo el servidor, hay que apagar el rmiregistry 
