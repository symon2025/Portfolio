package aed;
import java.util.*;

public class Heap<T extends Comparable<T>> {
    private int cantidad; // Cantidad de elementos
    private ArrayList<Handle<T>> littleHeap; // El heap esta conformado con un array de handles para referenciar a las TuplaUsers o transacciones y poder acceder a ellas sin recorrer todo el heap
    private ArrayList<Handle<T>> handlesEnOrden; // Array con los handles en orden, ya sea por id de usuarios o transaccion 

    public class Handle<T>{
        private T valor;
        private int indice; // esto me indica donde estoy en el heap

        public Handle(T valor, int indice) {
            this.valor = valor;
            this.indice = indice;
        }

        public T getValor() {
            return valor;
        }

        // metodo para actualizar el valor
        public void actualizarValor(T valorNuevo){
            valor = valorNuevo;
            orden(indice); // compljidad --> O(logaritmica)
        }

       public void eliminarMayor() {
            swap(0, cantidad - 1);      // swap con el último --> O(1)
            cantidad--;                 // descartás el último
            orden(0);                   // reordenás desde i --> complejidad O(log n) solo acomodo un elemento, en este caso el ultimo que sería el mas chico, queda como el mayor cuando es el menor
        }
    }

    public Heap(int n_usuarios, ArrayList<T> lista){
        this.cantidad = n_usuarios; // complejidad O(1)
        this.handlesEnOrden = new ArrayList<Handle<T>>(n_usuarios); //Preparo el array donde estan los users en ORDEN --> complejidad O(1)
        this.littleHeap = new ArrayList<Handle<T>>(n_usuarios); // Preparo el heap donde estaran los users ORDENADOS POR MONTO --> complejidad O(1)
        for (int i=0;i< n_usuarios;i++){ // esto es O(P), donde p es la cantidad de usuarios
            littleHeap.add(new Handle<T>(lista.get(i), i)); //Agrego en el heap un Handle de cada User  --> complejidad O(1)
            this.handlesEnOrden.add(this.littleHeap.get(i)); //Agrego en el array el handle de cada user  --> complejidad O(1)
        }
        ordenDeFloyd(0); //Ordeno el heap, el heapify de un array, tarda O(p)

        //TOTAL: O(p)
        
    }

    public ArrayList<Handle<T>> handlesOrdenados(){ //Retorno el array
        return handlesEnOrden;  // retornar un array ya creado --> O(1)
    }
    
    private void swap(int i, int j) { //i y j son los indices de elementos que comparo respectivamente
        //Cambio la posicion como el valor en si
        Handle<T> valor = littleHeap.get(j);
        
        littleHeap.set(j, littleHeap.get(i));
        littleHeap.get(j).indice = j;
        
        littleHeap.set(i, valor);
        littleHeap.get(i).indice = i;
        // es todo O(1)

}
    

    private void orden(int i) {

        while (2*i + 2 < cantidad){ // caso actual tiene dos hijos
            T actual = littleHeap.get(i).valor;
            T hijoIzq = littleHeap.get(2*i + 1).valor;
            T hijoDer= littleHeap.get(2*i + 2).valor;

            
            if(hijoDer.compareTo(hijoIzq) > 0 ){ //Verifico cual es el hijo mayor para comparar ese con el padre
                if (actual.compareTo(hijoDer)<0){//Verifico si efectivamente el hijo dicho es mayor que el padre
                swap(i,2*i + 2); //Ejecuto el cambio de posicion

                i = 2*i +2;//Edito las posiciones de la funcion orden
                }

                else{break;} //Si el hijo es menor al padre, entonces rompo el bucle
            }
            else{//Este es el caso que el hijo izquiero es el mayor
                if (actual.compareTo(hijoIzq)<0){//Verifico si efectivamente el hijo dicho es mayor que el padre
                swap(i,2*i+1); //Ejecuto el cambio de posicion
                i = 2*i +1;//Edito las posiciones de la funcion orden
                }

                else{break;} //Si el hijo es menor al padre, entonces rompo el bucle
            }
        }
        
        if (2*i + 1 < cantidad){ // Verifico el caso de que solo tenga el hijo izquierdo
            T actual = littleHeap.get(i).valor;
            T hijoIzq = littleHeap.get(2*i + 1).valor;
            
            if (actual.compareTo(hijoIzq) < 0){
                swap(i,2*i+1);//Si el hijo es mayor, entonces lo cambio de posicion
                i = 2*i +2; //Edito las posiciones en la funcion
                }
        }
        
        while (i > 0 && i < cantidad){ // mientras el elemento no sea la raiz, voy subiendo el hijo si este es mayor
            T actual = littleHeap.get(i).valor;
            T padre = littleHeap.get((i-1)/2).valor;

            
            if(actual.compareTo(padre)>0 ){
                swap(i,(i-1)/2); //Si el padre es menor, entonces lo cambio de posicion
                //Edito las posiciones
                i = (i-1)/2;
            }
            else{break;} //Si el padre es mayor, entonces rompo el bucle
        }
        //COMPLEJIDAD TOTAL = O(log n) }, con n = a la cantidad de elementos
            
    }

    private void ordenDeFloyd(int i) {//Hace el algoritmo de orden de Floyd
        if (2 * i + 1 < cantidad) ordenDeFloyd(2 * i + 1); // hijo izquierdo
        if (2 * i + 2 < cantidad) ordenDeFloyd(2 * i + 2); // hijo derecho
        int maxIndex = i;
    
        if (2 * i + 1 < cantidad) {
            if (littleHeap.get(2 * i + 1).getValor().compareTo(littleHeap.get(maxIndex).getValor()) > 0) {
                maxIndex = 2 * i + 1;
            }
        }
    
        if (2 * i + 2 < cantidad) {
            if (littleHeap.get(2 * i + 2).getValor().compareTo(littleHeap.get(maxIndex).getValor()) > 0) {
                maxIndex = 2 * i + 2;
            }
        }
    
        if (maxIndex != i) {
            swap(i, maxIndex);
        }
        //COMPLEJIDAD TOTAL, de hacer heapify = O(n), donde n es la cantidad de elementos
    }
    
    public Handle<T> mayor(){
        return littleHeap.get(0); // complejidad --> O(1) simplemente obtengo la raiz del heap
    }

}
