#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;
 
 
string inverso(string palabra){
    reverse(palabra.begin(), palabra.end());
    return palabra;
}
 
long long minCosto(int cant_palabras,vector<long long> costos, vector<string> palabras,vector<string> invertidas){
    long long matriz_costo_total[cant_palabras][2];
     for(int i = 0;i < cant_palabras;i++){
        for(int j = 0;j<2;j++){
            matriz_costo_total[i][j] = 0;
        }
    }
    
    matriz_costo_total[0][0] = 0;
    matriz_costo_total[0][1] = costos[0];
 
    for(int i = 1;i < cant_palabras;i++){
        matriz_costo_total[i][0] = LONG_LONG_MAX;
        matriz_costo_total[i][1] = LONG_LONG_MAX;
 
        if(palabras[i-1] <= palabras[i]){
            matriz_costo_total[i][0] = min(matriz_costo_total[i][0],matriz_costo_total[i-1][0]);
        }
    
        if(invertidas[i-1] <= palabras[i]){
            matriz_costo_total[i][0] = min(matriz_costo_total[i][0],matriz_costo_total[i-1][1]);
        }
 
        if(palabras[i-1] <= invertidas[i]){
            if(matriz_costo_total[i-1][0]+costos[i] >=0){
                matriz_costo_total[i][1] = min(matriz_costo_total[i][1],matriz_costo_total[i-1][0]+costos[i]);
            }
        }
        if(invertidas[i-1] <= invertidas[i]){
            if(matriz_costo_total[i-1][1]+costos[i] >=0){
                matriz_costo_total[i][1] = min(matriz_costo_total[i][1],matriz_costo_total[i-1][1]+costos[i]);
            }
        }
    }
    
    long long res = min(matriz_costo_total[cant_palabras-1][0],matriz_costo_total[cant_palabras-1][1]);

    if(res == LONG_LONG_MAX){
        return -1;
    }
    return res;
}


int main(){
    int cant_palabras;
    cin>> cant_palabras;
    vector<long long> mis_costos(cant_palabras,0);
    vector<string> mis_palabras(cant_palabras,"");
    vector<string> mis_inversas(cant_palabras,"");
 
    for(int i = 0;i < cant_palabras;i++){
        cin>> mis_costos[i];
    }
    for(int i = 0; i < cant_palabras;i++){
        cin>> mis_palabras[i];
    }
    for(int i = 0; i < cant_palabras;i++){
        mis_inversas[i] = inverso(mis_palabras[i]);
    }
 
    cout<< minCosto(cant_palabras,mis_costos,mis_palabras,mis_inversas);
    
    return 0;
}