package py.edu.uca.fcyt.toluca.statusGame;

import py.edu.uca.fcyt.toluca.game.*;


/** Guarda y controla el estado de un jugador en la mano
 */
//package py.edu.uca.fcyt.toluca;
public class StatusPlayer{
    private TrucoCard[] cartas;
    private boolean primeraCarta;//Guarda verdadero si el jugador jugo la primera carta
    private int cCartas; //Indica la cantidad de cartas que tiene el jugador
    private int cantidadDeCartasJugadas;/*julio coloco este atributo porque no entendio el cCartas como funcionaba*/
    private boolean seCerro;
    /**
     */    
    public boolean envido;
    public boolean flor;
    /** Indica si el jugador canto o no flor
     */
    public boolean cantoFlor;
    /** indica si el jugador canto o no envido
     */    
    public boolean cantoEnvido;

    /** Constructor por omision, crea una nueva instancia de StatusPlayer
     */    
    public StatusPlayer(){
        seCerro=primeraCarta=envido=flor=cantoEnvido=cantoFlor=false;
        cartas=new TrucoCard[3];
        cCartas=0;
        cantidadDeCartasJugadas = 0;
    }
    /** Guarda un el estado de cierre del jugador. El jugador que se cierra no puede jugar mas
     */    
    public void cerrar()
    {
        seCerro=true;
    }
    public boolean estaCerrado()
    {
        return seCerro;
    }
    /** Recive la carta cuando se le reparte y la guarda
     * @return Verdadero si Jugo ya alguna carta, falso si no
     */
    public boolean jugoPrimeraCarta (){
        return primeraCarta;
    }
    
    public boolean jugoTresCartas (){
        
        System.out.println("cartas jugadas" + cCartas);
        if(cantidadDeCartasJugadas==3)
            return true;
        else
            return false;
    }
    
    /** Recibe las cartas cuando se reparten
     * @param Cual Es la carta que se recibe
     */    
    public void agregarCarta(TrucoCard Cual){
        cartas[cCartas]=Cual;
        cCartas++;
    }
    /** Retorna un vector de tres con las cartas del jugador
     * @return Vector de con 3 TrucoCards que son las cartas del jugador
     */    
    public TrucoCard[] mostrarCartas()
    {
        return cartas;
    }
    //Imprime un estado borrar para publicar
    /** Imprime un estado de las cartas de jugador en la consola "QUITAR"
     */
    public void statusPrint(){
        System.out.println("Cartas");
        for(int i=0;i<3;i++){
            System.out.print("Palo: ");
            System.out.print(cartas[i].getKind());
            System.out.print(" - ");
            System.out.print("Valor: ");
            System.out.println(cartas[i].getValue());
            System.out.print(" Jugada: ");
            System.out.println(cartas[i].isFlipped());
        }
            System.out.println(puedeCantarFlor());
     }
    
    private int valor_envido(TrucoCard cual)
    {// Devuelve el valor de la carta en el envido
        if(cual.getValue()>=10)
            return 0;
        else
            return cual.getValue();
    }

    private boolean son_mismo_palo(TrucoCard uno,TrucoCard dos)
    { //Verdadero si son del mismo palo
        if(uno.getKind()==dos.getKind())
            return true;
        else
            return false;
    }
    /** verifica si puede cantar ese tanto con alguna combinacion de dos carta del mismo palo
     * @param cuales
     * @param cuanto 
     * @return  */
    private boolean contar_tanto(TrucoCard[] cuales, int cuanto)
    {
        
        System.out.println("verificando en contar tanto");
        if (son_mismo_palo(cuales[0],cuales[1]) && valor_envido(cuales[0]) + valor_envido(cuales[1])+20 == cuanto){
            return true;
        }
        if (son_mismo_palo(cuales[0],cuales[2]) && valor_envido(cuales[0]) + valor_envido(cuales[2])+20 == cuanto){
            return true;
        }
        if (son_mismo_palo(cuales[1],cuales[2]) && valor_envido(cuales[1]) + valor_envido(cuales[2])+20 == cuanto){
            return true;
        }
        System.out.println("resultado falso;");
        return false;                                
    }
    /** devuelve el envido mayor del jugador
     * @return  el valor del envido - deprecated for Julio Rey*/
    /*public int contarTanto()
    {
        return contar_tanto(cartas);
    }*/
    /** Devuelve Verdadero si puede cantar envido del valor de cuanto
     * @param cuanto indica cuento es el valor del envido
     * @return  verdadero si es cierto que puede cantar eso*/
    public boolean puedeCantarEnvido(int cuanto)
	//Devuelve verdadero si el jugador player puede cantar cuanto de envido
    {
         //System.out.println ("conte " + contar_tanto(cartas) +"\n y me preguntaron " + cuanto );
        if(cuanto>=20 && contar_tanto(cartas,cuanto)){	
            System.out.println ("paso la primera purueba");
            return true;}
        else{
            if(cuanto<20)
                for(int i=0;i<3;i++)
                    if(cartas[i].getValue()==cuanto)
                        return true;
            return false;
        }
    }
    /** Devuelve Verdadero si el jugador tiene flor
     * @return  verdadero si el jugador puede cantar o si tiene una flor
     */
    public boolean puedeCantarFlor()
    {
        System.out.println("verificando puede cantar flor: "+cartas[0].getKind()+cartas[1].getKind()+cartas[2].getKind());
        if(cartas[0].getKind()==cartas[1].getKind() && cartas[0].getKind()==cartas[2].getKind())
            return true;
        return false;
    }
    /** Cuenta el valor de la flor del jugador
     * @return Retorna un int con el valor de la flor o 0 en caso que el jugador no tenga flor
     */    
    public int contarFlor()
    {
        if(puedeCantarFlor()){
            return (valor_envido(cartas[0])+valor_envido(cartas[1])+valor_envido(cartas[2]));
           }
        else 
            return 0;
    }
    /** Marca una carta como jugada
     * @param cual indica que carta se va a jugar
     * @return  devuelve verdadero si se pudo jugar o falso y ocurrio un error (la carta no existe o ya se jugo)*/
    public boolean jugarCarta (TrucoCard cual)
    {
        for(int i=0;i<3;i++){
            if(cartas[i]==cual && cartas[i].isFlipped()==false){
                cartas[i].setFlipped(true);
                cantidadDeCartasJugadas++;
                primeraCarta=true;
                return true;
            }
        }
        return false;
    }
    
    /** Verifica si el jugador puede jugar una carta
     * @param cual Es la carta que el jugador desea jugar
     * @return Devuelve 1 si puede jugar, 0 si ya se jugo y -1 si el jugador no tiene esa carta
     */    
    public int puedeJugarCarta(TrucoCard cual)
    {//Busca la carta y verifica si la carta no se jugo ya
        System.out.println("puedeJugarCarta - sp palo " + cual.getKind() + " nro:" +cual.getValue());
        if(!seCerro){           
        for(int i=0;i<3;i++){
            System.out.println("verificando - sp palo " + cartas[i].getKind() + " nro:" +cartas[i].getValue());
            if(cartas[i]==cual && cartas[i].isFlipped()==false){
                System.out.println("puedeJugarCarta - Puede Jugar - sp");
                return 1;}
            else if(cartas[i]==cual && cartas[i].isFlipped()==true){
                System.out.println("puedeJugarCarta - NO PUEDE JUGAR- sp");
                return 0;}
        }   
        System.out.println("puedeJugarCarta - No encontro la carta- sp");
        }
        System.out.println("puedeJugarCarta - Se cerro?- sp");
        return -1;
    }
}
