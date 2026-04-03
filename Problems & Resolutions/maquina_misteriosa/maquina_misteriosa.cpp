#include <iostream>
#include <vector>
#include <queue>
#include <set>
#include <map>
using namespace std;
 
vector<int> vecinos(int v){
    vector<int> res;

    if(v % 2 == 0 && v/2 > 0){
        res.push_back(v/2);
    }
    if(v >0 && v+1 <= 20000){
        res.push_back(v+1);
    }
    
    return res;
}
 
int bfs(int inicio, int fin){
    if(inicio == fin){
        return 0;
    }
 
    int cant_clicks = 0;
    queue<int> cola;
    map<int,int> distancias;
    set<int> visitados;
 
    cola.push(fin);
    distancias[fin] = 0;
    visitados.insert(fin);
 
    while (not cola.empty()){
        int v = cola.front();
        cola.pop();
 
        vector<int> vecinos_v = vecinos(v);
 
        for(int u : vecinos_v){
            if(visitados.count(u) != 1){
                distancias[u] = distancias[v] + 1;
                visitados.insert(u);
                cola.push(u);
            }
            if(u == inicio){
                return distancias[u];
            }
        }
    }
    return -1;
}

int main(){
    int n;
    int m;
 
    cin>>n;
    cin>>m;
 
    cout<<bfs(n,m);
 
    return 0;
}