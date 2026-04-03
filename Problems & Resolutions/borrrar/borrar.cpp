#include <iostream>
#include <vector>
#include <string>
using namespace std;

 
int borrar(int cant_letras,string s){
 
    int matriz[cant_letras][cant_letras];
 
    for(int i = 0; i < cant_letras;i++){
        matriz[i][i] = 1;
    }

    for(int i = 1; i < cant_letras;i++){

        for(int j = i; j < cant_letras;j++){
            matriz[j-i][j] = INT_MAX;
 
            if(s[j-i] == s[j]){
                matriz[j-i][j] = min(matriz[j-i][j-1],matriz[j-i+1][j]);
            }
            else{
                 matriz[j-i][j] = min(matriz[j-i][j-1]+1,matriz[j-i+1][j]+1);
            }
            for(int k = j-i+1; k < j; k++){
                matriz[j-i][j] = min(matriz[j-i][j], matriz[j-i][k] + matriz[k+1][j]);
            }
        }
 
    }
    return matriz[0][cant_letras-1];
}
 
 
int main(){
    int cant_letras;
    string s;
 
    cin>>cant_letras;
    cin>>s;
 
    cout<<borrar(cant_letras,s);
 
    return 0;
}