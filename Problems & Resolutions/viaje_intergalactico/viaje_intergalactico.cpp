#include <iostream>
#include <vector>
#include <queue>
using namespace std;
 
int siguiente_tiempo(vector<vector<int>> &tiempos_viajeros, int u, int tiempo){
    auto iterador_comienzo = lower_bound(tiempos_viajeros[u].begin(),tiempos_viajeros[u].end(),tiempo);
    int i = iterador_comienzo - tiempos_viajeros[u].begin();
 
    for(int j = i;j<tiempos_viajeros[u].size();j++){
        if(tiempo == tiempos_viajeros[u][j]){
            tiempo += 1;
        }
    }
    return tiempo;
}
 
int dijkstra(vector<vector<pair<int,int>>> grafo, vector<vector<int>> tiempos_viajeros, int n){
    vector<int> distancias (n+1,INT_MAX);
    priority_queue<pair<int,int>> cola;
    distancias[1] = 0;
 
    cola.push({0,1});
 
    while(not cola.empty()){
        pair<int,int> min = cola.top();
        cola.pop();
        int tiempo_llegada_u = -min.first;
        int u = min.second;
        int tiempo_salida = siguiente_tiempo(tiempos_viajeros,u,tiempo_llegada_u);
        
        if(tiempo_llegada_u > distancias[u]){
            continue;
        }

        for(int i = 0; i< grafo[u].size();i++){   //para cada vecino de u
            int v = grafo[u][i].first; //vecino v de u
            int costo = grafo[u][i].second; //costo de tiempo para ir de u a ve (y viceversa)
            int tiempo_llegada = tiempo_salida + costo;
            
            if(tiempo_llegada < distancias[v]){
                distancias[v] = tiempo_llegada;
                cola.push({-distancias[v],v});
            }
        }
    }
    return distancias[n];
}
 
int main(){
    int planetas,planeta_v,planeta_w;
    int aristas_pares_planetas,costo_tiempo,cant_momentos,viajero_en_tiempo;
    pair<int,int> arista;
 
    cin>>planetas;
    cin>>aristas_pares_planetas;
 
    vector<vector<pair<int,int>>> lista_ady (planetas+1);
    for(int i = 1;i<=aristas_pares_planetas;i++){
        cin>>planeta_v;
        cin>>planeta_w;
        cin>>costo_tiempo;
        
        lista_ady[planeta_v].push_back({planeta_w,costo_tiempo});
        lista_ady[planeta_w].push_back({planeta_v,costo_tiempo});
    }
 
    vector<vector<int>> tiempos_viajeros (planetas+1);
    for(int i = 1; i <= planetas;i++){
        cin>>cant_momentos;
 
        if(cant_momentos == 0){
            continue;
        }
        for(int j = 0;j<cant_momentos;j++){
            cin>>viajero_en_tiempo;
            tiempos_viajeros[i].push_back(viajero_en_tiempo);
 
        }
    }
 
    int res = dijkstra(lista_ady,tiempos_viajeros,planetas);
    
    if(res == INT_MAX){
        cout<<-1;
    }
    else{
        cout<<res;
    }

    return 0;
}