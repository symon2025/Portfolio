package aed;
import java.util.ArrayList;

public class Berretacoin {
    private ListaEnlazada<Bloque> cadena; 

    private ArrayList<Heap<TuplaUser>.Handle<TuplaUser>> users;  // un arraylist de Handle<TuplaUser>
    private Heap<TuplaUser> usersxmonto; // Heap (de tuplas usuario/monto, ordenado por mayor monto y de menor id en desempate)

    private Heap<Transaccion> bloquexmonto; // Heap (de transacciones, ordenado por mayor monto y de mayor id en desempate)
    //Por handle esta conectado al ultimo bloque
    
    public Berretacoin(int n_usuarios){
        this.cadena = new ListaEnlazada<>(); // Creo la cadena vacía --> O(1)

        ArrayList<TuplaUser> usuarios = new ArrayList<>(n_usuarios);//Creo el array con usuarios  --> O(1)

        for (int i=1;i <= n_usuarios;i++){ // esto es un for que va desde 1 hasta el ultimo usuario por lo tanto la complejidad es O(p), donde p es la cantidad de usuarios del sistema
            usuarios.add(new TuplaUser(i,0)); // agregar a un array de longitud ya definida cuesta O(1) cada vez
        }

        usersxmonto = new Heap<TuplaUser>(n_usuarios, usuarios); // Creo un heap con users  --> complejidad O(p) (justificado en el constructor del heap)
        this.users = usersxmonto.handlesOrdenados(); // Retorno el users con handle, creado en la clase Heap  --> complejidad O(1)

        //TOTAL: 2*O(p) = O(p)
        
    }

    public void agregarBloque(Transaccion[] transacciones){
        
        Bloque bloque = new Bloque(); // Creo un nuevo Bloque, que representara una lista enlazada de las transacciones a añadir --> O(1) 
        int posicion; // Inicializo la variable posicion
        ArrayList<Transaccion> trans_copia = new ArrayList<>(transacciones.length);// Una lista en orden para crear el heap de transacciones, de tamaño ya dado  --> complejidad O(1)

        for(int i=0;i< transacciones.length;i++){// Itero las transacciones una por una, realizando las operaciones descritas abajo --> O(nb * (log P + 1)) = O(nb * log P)
            bloque.agregarTrans(transacciones[i]);// Agrego la transaccion al Bloque --> O(1)

            posicion = transacciones[i].id_vendedor()-1; // posiscion del vendedor --> O(1)
            Heap<TuplaUser>.Handle<TuplaUser> handle_vendedor = users.get(posicion); // Obtengo el handle del vendedor del array users gracias a su posicion --> O(1)
            TuplaUser vendedor = handle_vendedor.getValor(); // Obtengo la tupla de dicho vendedor --> O(1)
            handle_vendedor.actualizarValor(new TuplaUser(vendedor.ID(), vendedor.monto() + transacciones[i].monto())); // Actualizo el saldo del vendedor --> O(log P)
            
            if(transacciones[i].id_comprador() != 0){// Verifico que no sea de creacion, accediendo dentro de la transaccion actual --> O(1)

            posicion = transacciones[i].id_comprador()-1; // Posicion del comprador --> O(1)
            Heap<TuplaUser>.Handle<TuplaUser> handle_comprador = users.get(posicion); // Obtengo el handle del comprador del array users gracias a su posicion --> O(1)
            TuplaUser comprador = handle_comprador.getValor(); // Obtengo la tupla de dicho comprador --> O(1)
            handle_comprador.actualizarValor(new TuplaUser(comprador.ID(), comprador.monto() - transacciones[i].monto())); // Actualizo el saldo del comprador O(log P)
            }

            trans_copia.add(transacciones[i]);// Guardo la copia en orden en la lista (de tamaño dado en un principio) --> O(1)
        }

        bloquexmonto = new Heap<Transaccion>( transacciones.length , trans_copia); // Creo el heap con las transacciones, ordenado por monto --> O(nb)

        cadena.agregarAdelante(bloque); // Agrego cada bloque adelante en la cadena --> O(1)
    }

    // TOTAL: O(nb * log P) + O(nb) + O(1) --> O(nb * log P)

    public Transaccion txMayorValorUltimoBloque(){
        return bloquexmonto.mayor().getValor();// Accedo a la raiz del bloquexmonto, obteniendo un handle, y tomando su valor (que es una transaccion) --> O(1)
    }

    // TOTAL: O(1)
    
    public Transaccion[] txUltimoBloque(){
        Bloque ultBloque = cadena.obtenerPrimero(); // Tomo el ultimo elemento
        Bloque copia = new Bloque(ultBloque); // Lo copio para evitar aliassing --> Complejidad O(cant transacciones a copiar)

        Transaccion[] bloque = new Transaccion[ultBloque.longitud()]; // Creo un array de transacciones donde ir colocando las de la copia de ultbloque --> O(1)


        for (int i=0;i< ultBloque.longitud();i++){ // Itero los elementos uno por uno, seteando en cada posicion del array la transaccion correspondiente de la copia --> O(nb)
            bloque[i] = copia.obtenerPrimerTrans();// Agarro el array en el indice actual y coloco la primer transaccion de la copia del Bloque --> O(1)
            copia.eliminarPrimerTrans(); // Elimino la primer transaccion para seguir con la iteracion --> O(1)
        }
        
        return bloque;
    }
    
    // TOTAL: O(nb)


    public int maximoTenedor(){
        return usersxmonto.mayor().getValor().ID();// Me quedo con la ID del elemento de mas arriba del heap --> O(1)
    }

    // TOTAL: O(1)

    public int montoMedioUltimoBloque() {
        return cadena.obtenerPrimero().mediaBloque(); // Obtengo el ultimo bloque (primero en la cadena) y le calculo la media --> O(1)
    }

    // TOTAL: O(1)


    public void hackearTx() {
        Heap<Transaccion>.Handle<Transaccion> mayorTransaccion = bloquexmonto.mayor(); //obtengo la transaccion mas grande del heap
        int posicion;

        posicion = mayorTransaccion.getValor().id_vendedor() - 1; // Posición del vendedor --> O(1)
        Heap<TuplaUser>.Handle<TuplaUser> handle_vendedor = users.get(posicion); // Obtengo el handle --> O(1)
        TuplaUser vendedor = handle_vendedor.getValor(); // Obtengo la tupla user --> O(1)
        handle_vendedor.actualizarValor(
        new TuplaUser(vendedor.ID(), vendedor.monto() - mayorTransaccion.getValor().monto()) // Actualizo el saldo restandole al vendedor lo que habia ganado
        // complejidad de actualizar valor O(log p)
        );

        
        if (mayorTransaccion.getValor().id_comprador() != 0) { // Verifico si la transaccion no es de creacion
            posicion = mayorTransaccion.getValor().id_comprador() - 1; // Posicion del comprador
            Heap<TuplaUser>.Handle<TuplaUser> handle_comprador = users.get(posicion); // Obtengo el handle del comprador en users
            TuplaUser comprador = handle_comprador.getValor();
            handle_comprador.actualizarValor(
            new TuplaUser(comprador.ID(), comprador.monto() + mayorTransaccion.getValor().monto()) // Actualizo su saldo devolviendole lo que habia gastado
            );
            // Idem lo de vendedor, complejidad total O(log p)
        }

        // Eliminamos del heap de transacciones
        mayorTransaccion.eliminarMayor(); // complejidad O(log n), justificado en eliminar mayor

        cadena.obtenerPrimero().eliminar(mayorTransaccion); // Obtengo el ultimo bloque de la cadena y elimino la mayor transaccion del mismo en O(1) ya que tengo su referencia directa y no tengo que buscarla dentro del bloque
        //COMPLEJIDAD TOTAL = 2 * O(1) + 2 * O(log p) + O(log n) = O(log p + log n)
        }
        
}



