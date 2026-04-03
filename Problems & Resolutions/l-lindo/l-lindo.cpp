#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;
 

int cant_movimientos_l_lindo(string palabra, char l,int largo){

   if(largo == 1 and palabra[0] == l){
        return 0;
    }
    else if(largo == 1){
        return 1;
    }
 
    int mid = largo / 2;
 
    int mov_caso_2 = 0;
    int mov_caso_3 = 0;
 
    for(int i = 0; i < mid; i++){ 
        if(palabra[i] != l){
            mov_caso_2 += 1;
        }
    }
    
    mov_caso_2 += cant_movimientos_l_lindo(palabra.substr(mid,mid), l+1, mid);
 
    for(int i = mid; i < largo; i++){
        if(palabra[i] != l){
            mov_caso_3 += 1;
          }
        }

    mov_caso_3 += cant_movimientos_l_lindo(palabra.substr(0,mid), l+1,mid);
    
    return min(mov_caso_2,mov_caso_3);
}
 
 
int main(){
    int tests;
    int largo;
    string palabra;
 
    cin >>tests;
 
    for(int i = 0; i < tests-1;i++){
    cin >>largo;
    cin >> palabra;
    
    cout <<cant_movimientos_l_lindo(palabra,'a',largo)<<endl;
    }

    cin >>largo;
    cin >> palabra;
    cout <<cant_movimientos_l_lindo(palabra,'a',largo);
}