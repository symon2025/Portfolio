#include <iostream>
#include <vector>
#include <string>
using namespace std;
 
 
bool convertir(long x, long y, string& transformaciones){
 
    if( x == y){
        return true;
    }
    if( y-x < 0){
        return false;
    }
 
    bool comparador;

    if(y%2 == 0){
        transformaciones.push_back('f');
        comparador = convertir(x,y/2,transformaciones);
 
        if (not comparador){
            transformaciones.pop_back();
        }
        return comparador;
    }
 
    if((y-1)%10 == 0){
        transformaciones.push_back('g');
        comparador = convertir(x,(y-1)/10,transformaciones);
 
        if(not comparador){
            transformaciones.pop_back();
        }
        return comparador;
    }
    
    if(transformaciones.size() <1){
        return false;
    }
 
    return false;
    
}
 
 
string salida_input(long x,long y,string transformaciones){
    int largo = transformaciones.size();
    if(largo == 0){
        return "NO";
    }
    
    string res = "" + to_string(x);
    for(int i = largo-1;i > -1;i--){
        if(transformaciones[i] == 'f'){
        res += " " + to_string(2*x);
        x = 2*x;
        }
        else{
        res += " "+ to_string(10*x+1);
        x = 10*x+1;
        }
    }
    return "YES\n" + to_string(largo+1) + "\n" + res; 
}
 
 
int main(){
    long x,y;
    cin>> x;
    cin>> y;
 
    string inicio = "";
    bool res = convertir(x,y,inicio);
    
    cout<<salida_input(x,y,inicio);
 
    return 0;
}