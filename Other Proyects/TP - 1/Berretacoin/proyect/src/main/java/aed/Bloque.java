package aed;

public class Bloque {
    private ListaEnlazada<Transaccion> bloque;
    private int cantTrans;
    private int montosTotal;

    public Bloque(){
        bloque = new ListaEnlazada<Transaccion>();
        cantTrans = 0;
        montosTotal = 0;
    }

    public ListaEnlazada<Transaccion>.Handle agregarTrans(Transaccion trans){
    ListaEnlazada<Transaccion>.Handle handle = bloque.agregarAtras(trans); // agrego la transaccion al bloque, lo cual me devuelve un handle que contiene la referencia al nodo en bloque  --> complejidad O(1)
    trans.setHandle(handle); // agrego el handle en la transaccion, entonces ahora hace referencia a si misma dentro del bloque  --> complejidad O(1)
        if (trans.id_comprador() != 0){ // verifico si la transaccion no es de creacion para sumarla al promedio --> O(1)
            cantTrans += 1;
            montosTotal += trans.monto(); // accedo al monto de la transaccion --> O(1)
        }
        return handle;
    }

    // TOTAL: O(1)


public void eliminarPrimerTrans(){ //  --> complejidad O(1)
        bloque.eliminarPrimero(); // accede a la lista enlazada que represesnta al Bloque --> O(1)
    }

    // TOTAL: O(1)


    public Transaccion obtenerPrimerTrans(){
        return bloque.obtenerPrimero(); // obtiene el primer elemento de la lista enlazada que representa el Bloque --> O(1)
    
    }
    
    public int mediaBloque(){
        if (this.cantTrans == 0) { // retorna 0 si no hay transacciones en el Bloque --> O(1)
            return 0;
            }
            
        int res = montosTotal / cantTrans; // divide monto total sobre cant de transaccion --> O(1)
        return res;
    }
    // TOTAL: O(1)

    public void eliminar(  Heap<Transaccion>.Handle<Transaccion> mayorTrans){
        mayorTrans.getValor().getHandle().eliminar();
        Transaccion trans = mayorTrans.getValor();
        montosTotal -= trans.monto();
        if(cantTrans == 0){
        return;
        }else{
        cantTrans --;
        }
    }


public Bloque(Bloque otro){

    this.bloque = new ListaEnlazada<>(); //crea una nueva lista enlazada --> O(1)
    this.cantTrans = otro.cantTrans; // copia la cant de transaccions del Bloque "otro" --> O(1)
    this.montosTotal = otro.montosTotal; // copia el monto total del Bloque "otro" --> O(1)

    if (otro.bloque.longitud() == 0){  //retorna la longitud del bloque --> O(1)
        return;
    }

    // TOTAL: O(1)
          

    ListaEnlazada<Transaccion>.Iterador it = otro.bloque.iterador(); // crea un iterador para el bloque --> O(1)
    while (it.haySiguiente()) { // itera por cada elemento del Bloque "otro" y agregandola al nuevo bloque --> O(nb)
        Transaccion original = it.obtener(); // obtiene la transaccion de la posicion del iterador --> O(1)
        Transaccion copia = new Transaccion( // copia cada uno de sus valores en una nueva transaccion copia --> O(1) + O(1) + O(1) + O(1) = O(1)
            original.getId(),
            original.id_comprador(),
            original.id_vendedor(),
            original.monto()
        );
        this.bloque.agregarAtras(copia); // agrega la transaccion al nuevo Bloque "otro" --> O(1) 
        it.siguiente(); // va al siguiente elemento --> O(1)
    }
}

// TOTAL: O(1) + O(nb) = O(nb)

public int longitud(){ 
    return bloque.longitud(); //retorna la longitud de la lista enlazada que representa el Bloque --> O(1)
} 
// TOTAL: O(1)

}

