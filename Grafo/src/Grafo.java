/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jose
 */
public class Grafo {
    NodoVertice vertice;
    
    public Grafo(){
        vertice=null;
    }
    public boolean insertarVertice(char dato){
        NodoVertice nuevo= new NodoVertice(dato);
        if(nuevo==null) return false;
        
        if(vertice==null){
            vertice=nuevo;
            return true;
        }
        //el nuevo se enlaza al final de la lista de VERTICE
        irUltimo();
        vertice.sig=nuevo;
        nuevo.ant=vertice;
        return true;
    }

    private void irUltimo() {
        while(vertice.sig!=null){
            vertice=vertice.sig;
        }
    }
    private void irPrimero(){
        while(vertice.ant!=null){
            vertice=vertice.ant;
        }
    }
    
     public int VerticesExistentes() {
        if (vertice == null) return 0;
        int x = 0;
        irPrimero();
        NodoVertice NoMeRepruebesBenigno = vertice;
        while (NoMeRepruebesBenigno != null) {
            x++;
            NoMeRepruebesBenigno = NoMeRepruebesBenigno.sig;
        }

        return x;
    }
     
    private NodoVertice buscarVertice(char dato){
        if(vertice==null) return null;
        irPrimero();
        for(NodoVertice buscar=vertice; buscar!=null; buscar=buscar.sig){
            if(buscar.dato==dato){
                return buscar;
            }
        }
        return null;
    }
    public boolean insertarArista(char origen, char destino){
        NodoVertice nodoOrigen=buscarVertice(origen);
        NodoVertice nodoDestino=buscarVertice(destino);
        
        if(nodoOrigen==null || nodoDestino==null){
            return false;
        }
        
        return nodoOrigen.insertarArista(nodoDestino);
    }
    public boolean eliminarArista(char origen, char destino){
        NodoVertice nodoOrigen=buscarVertice(origen);
        NodoVertice nodoDestino=buscarVertice(destino);
        if(nodoOrigen==null || nodoDestino==null){
            return false;
        }
        return nodoOrigen.eliminarArista(nodoDestino);
    }
    public boolean unSoloVertice(){
        return vertice.ant==null && vertice.sig==null;
    }
    public boolean eliminarVertice(char dato){
        if(vertice==null) return false;
        NodoVertice temp=buscarVertice(dato);
        if(temp==null) return false;
        
        //1. que el vertice no tenga aristas a otros vertices
        if(temp.arista!=null) return false;
        //que otros vertices no tengan arista a este vertice a eliminar
        quitaAristasDeOtrosVertice(temp);
        //Esta temp en el 1ero?
        if(temp==vertice){
            if(unSoloVertice()) vertice=null;
            else{
                vertice = temp.sig;
                temp.sig.ant=temp.sig=null;
            }
            return true;
        }
         //esta en el ultimo?
         if(temp.sig==null){
             temp.ant.sig=temp.ant=null;
             return true;
         }
         //temp esta en medio
         temp.ant.sig=temp.sig;
         temp.sig.ant=temp.ant;
         temp.sig=temp.ant=null;
         return true;
    }

    private void quitaAristasDeOtrosVertice(NodoVertice NodoEliminar) {
        irPrimero();
        for(NodoVertice buscar=vertice; buscar!=null; buscar=buscar.sig){
            buscar.eliminarArista(NodoEliminar);
        }
    }
    
    public String Camino(char camino[]){
        String cad = "";
        int i;
        for(i=0; i < camino.length - 1; i++){
            if(buscarVertice(camino[i]).buscarArista(buscarVertice(camino[i + 1])) != null){
                cad = "Camino existente";
            }else{
                cad = "Camino inexistente";
            }
        }
        return cad;
    }
    
    public boolean[][] matrizAdyacencia() {
        if (vertice == null) return null;
        int r,c;
        boolean matriz[][] = new boolean[VerticesExistentes()][VerticesExistentes()];
        for (r = 0; r < VerticesExistentes(); r++) {
            for (c = 0; c < VerticesExistentes(); c++) {
                matriz[r][c] = false;
            }
        }
        for (r = 0; r < VerticesExistentes(); r++) {
            c = 0;
            while (r != c) {
                c++;
                vertice = vertice.sig;
            }
            NodoArista auxilio = vertice.arista;
            irPrimero();
            while (auxilio != null) {
                c = 0;
                while (auxilio.direccion != vertice) {
                    vertice = vertice.sig;
                    c++;
                }
                matriz[r][c] = true;
                auxilio = auxilio.abajo;
                irPrimero();
            }
        }
        return matriz;
    }
    
    public String listaAdyacencia(char dato) {
        return buscarVertice(dato).toString();
    }
}
