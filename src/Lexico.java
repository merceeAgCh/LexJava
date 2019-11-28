

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;
import java.util.*;
/**
 *
 * @author MAC
 */
public final class Lexico {
    String tokensCompletos="";
    public Lexico(String f){
        String bufferIn;
        String ruta="/Users/emmanuelgarcia/Desktop/tiraTokens.txt";

        try{
            System.out.println(f);

            DataInputStream in=new DataInputStream(new FileInputStream(f));//leemos nuestro archivo de entrada
            try{

                while((bufferIn=in.readLine())!=null){//mientras no lleguemos al fin del archivo...
                    int i=0;
                    System.out.println(bufferIn);
                    String cad=bufferIn.trim();
                    //eliminamos los espacios en blanco al inicio o al final (pero no a la mitad)
                    while(i<cad.length()){//recorremos la línea
                        char t=cad.charAt(i);//vamos leyendo caracter por caracter
                        if(Character.isDigit(t)){//comprobamos si es un dígito
                            String ora="";
                            ora+=t;
                            int j=i+1;
                            if(cad.charAt(j)!='\0'){
                                while(Character.isDigit(cad.charAt(j))){
                                    //mientras el siguiente elemento sea un numero
                                    ora+=cad.charAt(j);//concatenamos
                                    j++;
                                    if(j==cad.length())break;//rompemos si llegamos al final de la línea
                                }
                            }
                            i=j;//movemos a nuestra variable i en la cadena
                            System.out.println("Número-->"+ora);
                            tokensCompletos+="Numero~"+ora+"#|";
                            continue;//pasamos al siguiente elemento
                        }//end if si es Dígito
                        else if(Character.isLetter(t)){//comprobamos si es una letra
                            String ora="";
                            ora+=t;
                            int j=i+1;
                            while(Character.isLetterOrDigit(cad.charAt(j))){
                                //mientras el siguiente elemento sea una letra o un digito
                                //ya que las variables pueden ser con números
                                ora+=cad.charAt(j);
                                j++;
                                if(j==cad.length())break;
                            }
                            i=j;
                            if(palabraReservada(ora)){//comprobamos si es una palabra reservada
                                System.out.println("Palabra_reservada="+ora);
                                tokensCompletos+="Palabra_reservada_"+ora+"~"+ora+"#|";
                            }
                            else{//caso contrario es un identificador o variable
                                if(tipoDato(ora)){
                                    System.out.println("TipoDeDato-->"+ora);
                                    tokensCompletos+="TipoDeDato~"+ora+"#|";
                                }
                                else{
                                    System.out.println("Identificador-->"+ora);
                                    tokensCompletos+="Identificador~"+ora+"#|";
                                }
                            }
                            continue;
                        }//end if si es variable
                        else if(!Character.isLetterOrDigit(t)){
                            //si no es letra ni dígito entonces...
                            if(evaluarCaracter(t)){//¿es separador?
                                if(t=='('||t==')'||t=='['||t==']'||t=='{'||t=='}'){
                                    System.out.println("SeparadorPar-->"+evaluarSeparador(t));
                                    tokensCompletos+="SeparadorPar~"+evaluarSeparador(t)+"#|";
                                }
                                else{
                                    System.out.println("Separador-->"+evaluarSeparador(t));
                                    tokensCompletos+="Separador~"+evaluarSeparador(t)+"#|";
                                }
                            }else{//¿o es un operador?
                                if(CaracterDeTermio(t)==';'){
                                    System.out.println("CaracterDeTermino-->"+t);
                                    tokensCompletos+="CaracterDeTermino~"+t+"#|";
                                }
                                else{
                                    if(evaluarOperador(t)!=' '){
                                        System.out.println("Operador_"+tipoDeOperador(t)+"--> "+evaluarOperador(t));
                                        tokensCompletos+="Operador_"+tipoDeOperador(t)+"~"+evaluarOperador(t)+"#|";
                                    }
                                }
                            }
                            i++;
                            continue;
                        }//end if si es diferente de letra y dígito
                    }
                    tokensCompletos+="$";
                }//end while

                File archivo = new File(ruta);
                BufferedWriter bw;
                if(archivo.exists()) {
                    bw = new BufferedWriter(new FileWriter(archivo));
                    bw.write(tokensCompletos);

                }
                else {
                    bw = new BufferedWriter(new FileWriter(archivo));
                    bw.write(tokensCompletos);


                }
                System.out.println(tokensCompletos);
                bw.close();
                Sintactico();
            }catch(IOException e){}
        }catch(FileNotFoundException e){}
    }

    public void Sintactico() throws FileNotFoundException, IOException{
        int SeparadoresPend = 0;
        String bufferIn;
        ArrayList<String> palabras = new ArrayList<String>();
        String Palabra2;
        int Linea=0;
        int al=0;
        String Pendiente;
        String f="/Users/emmanuelgarcia/Desktop/tiraTokens.txt";
        DataInputStream in=new DataInputStream(new FileInputStream(f));

        try{
            while((bufferIn=in.readLine())!=null){
                String cad=bufferIn.trim();

                int cont=0;
                for (int i = 0; i < cad.length(); i++) { //recorre el string
                    if(cad.charAt(i) == '~'){ //retorna el index del donde esta el (
                        if(al==1){
                            palabras.add(" ");
                            al--;
                        }
                        palabras.add(bufferIn.substring(cont,i)); //toma todo lo que esta antes de ( hasta el |
                    }
                    if (cad.charAt(i)=='|'&& cad.charAt(i+1)=='$'){
                        cont=i+2;
                        al++;
                    }
                    else if(cad.charAt(i)=='|'){
                        cont=i+1;
                    }

                }

            }
            for (int j = 0; j < palabras.size(); j++) {
                if(palabras.get(j+1)==" "&&palabras.get(j+3)!=null){
                    Linea++;
                    j=j+2;
                }
                switch(palabras.get(j)){
                    case "TipoDeDato":
                        if("Identificador".equals(palabras.get(j+1))){
                            System.out.println("bien");
                        }
                        else{
                            System.out.println("tienes un error en la linea"+Linea+1 );
                        }
                        break;
                    case "Identificado":
                        if("Operador_asignacion".equals(palabras.get(j+1))&&"Operador_asignacion".equals(palabras.get(j+2))||"Operador_menor_que".equals(palabras.get(j+1))&&"Operador_asignacion".equals(palabras.get(j+2))||"Operador_asignacion".equals(palabras.get(j+1))&&"Operador_asignacion".equals(palabras.get(j+2))||"Operador_menor_que".equals(palabras.get(j+1))||"Operador_mayor_que".equals(palabras.get(j+1))){
                            System.out.println("bien");
                        }
                        else{
                            System.out.println("tienes un error en la linea"+Linea+1 );
                        }
                        break;

                    case "Operador_asignacion":
                        if("Numero".equals(palabras.get(j+1))){
                            System.out.println("bien");
                        }
                        else{
                            System.out.println("tienes un error en la linea"+Linea+1 );
                        }
                        break;
                    case "SeparadorPar"  :
                        if(SeparadoresPend<0){
                            SeparadoresPend--;
                        }
                        else{
                            SeparadoresPend++;
                        }
                        break;
                    case "Numero":
                        if("CaracterDeTermino".equals(palabras.get(j+1))||"SeparadorPar".equals(palabras.get(j+1))){
                            System.out.println("bien");
                        }
                        else{
                            System.out.println("tienes un error en la linea"+Linea+1 );
                        }
                        break;
                    case "Palabra_reservada_if":
                        if("SeparadorPar".equals(palabras.get(j+1))&&"Identificador".equals(palabras.get(j+2))&&"Operador_asignacion".equals(palabras.get(j+3))&&"Operador_asignacion".equals(palabras.get(j+4))||"Operador_menor_que".equals(palabras.get(j+3))&&"Operador_asignacion".equals(palabras.get(j+4))||"Operador_asignacion".equals(palabras.get(j+3))&&"Operador_asignacion".equals(palabras.get(j+4))||"Operador_menor_que".equals(palabras.get(j+3))||"Operador_mayor_que".equals(palabras.get(j+3))&&"Numero".equals(palabras.get(j+4))||"Numero".equals(palabras.get(j+5))){
                            System.out.println("bien");
                        }
                        else{
                            System.out.println("tienes un error en la linea"+Linea+1 );
                        }
                        break;
                }

            }
            if(SeparadoresPend!=0){
                System.out.println("tienes un error cierra todos los (,{ o {");
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
//leemos nuestro archivo de entrada
    }

    /**
     Método que evalua nuestro caracter si existe y nos retorna
     verdadero para los separadores
     y
     falso para los operadores
     */
    public static boolean evaluarCaracter(char c){
        if(c=='(') return true;
        else if(c==')') return true;
        else if(c=='[') return true;
        else if(c==']') return true;
        else if(c=='.') return true;
        else if(c==':') return true;
        else if(c==',') return true;
        else if(c=='"') return true;
        else if(c=='@') return true; //comentarios
        else if(c=='<') return false;
        else if(c=='>') return false;
        else if(c=='#') return false; // y logico
        else if(c=='°') return false; // o logico
        else if(c=='~') return false; // no logico
        else if(c=='$') return false; // asignacion
        else if(c=='+') return false;
        else if(c=='-') return false;
        else if(c=='*') return false;
        else if(c=='/') return false;
        else if(c=='%') return false; // mod
        else return false;
    }

    /**
     retornamos nuestro caracter de operador
     */
    public static char evaluarOperador(char c){
        char car=' ';
        if(c=='<')car='<';
        else if(c=='>')car='>';
        else if(c=='#')car='#';
        else if(c=='°')car='°';
        else if(c=='~')car='~';
        else if(c=='$')car='$';
        else if(c=='+')car='+';
        else if(c=='=')car='=';
        else if(c=='-')car='-';
        else if(c=='*')car='*';
        else if(c=='/')car='/';
        else if(c=='%')car='%';
        return car;
    }

    public void analisis(String value){
        System.out.println(value);
    }
    public static String tipoDeOperador(char c){
        String valor="";
        switch(c){
            case '<':
                valor="menor_que";
                break;
            case '>':
                valor="mayor_que";
                break;
            case '=':
                valor="asignacion";
                break;
            case '+':
                valor="suma";
                break;
            case '/':
                valor="division";
                break;
            case '-':
                valor="resta";
                break;
            case '*':
                valor="multiplicacion";
                break;

        }
        return valor;

    }
    public static char CaracterDeTermio(char c){
        char car=' ';
        if(c==';')car=';';
        return car;
    }


    public static char evaluarSeparador(char c){
        char car=' ';
        if(c=='(') car='(';
        else if(c==')')car=')';
        else if(c=='[')car='[';
        else if(c==']')car=']';
        else if(c=='"')car='"';
        else if(c=='.')car='.';
        else if(c==':')car=':';
        else if(c==',')car=',';
        else if(c=='@')car='@';

        return car;
    }
    public static boolean palabraReservada(String cad){
        if(cad.equalsIgnoreCase("public")) return true;
        else if(cad.equalsIgnoreCase("static"))return true;
        else if(cad.equalsIgnoreCase("else"))return true;
        else if(cad.equalsIgnoreCase("if"))return true;
        else if(cad.equalsIgnoreCase("for"))return true;
        else if(cad.equalsIgnoreCase("while")) return true;
        else if(cad.equalsIgnoreCase("return")) return true;
        else if(cad.equalsIgnoreCase("case"))return true;
        else if(cad.equalsIgnoreCase("break"))return true;
        else if(cad.equalsIgnoreCase("protected"))return true;
        else if(cad.equalsIgnoreCase("switch")) return true;
        else if(cad.equalsIgnoreCase("do")) return true;
        else if(cad.equalsIgnoreCase("foreach"))return true;
        else if(cad.equalsIgnoreCase("new"))return true;

            //con equalsIgnoreCase no nos importa si está en mayúsculas o minúsculas o alternadas
        else return false;
    }
    public static boolean tipoDato(String cad){
        if(cad.equalsIgnoreCase("int")) return true;
        else if(cad.equalsIgnoreCase("String"))return true;
        else if(cad.equalsIgnoreCase("boolean"))return true;
        else if(cad.equalsIgnoreCase("enum"))return true;
        else if(cad.equalsIgnoreCase("object"))return true;
        else if(cad.equalsIgnoreCase("char")) return true;
        else if(cad.equalsIgnoreCase("float")) return true;
        else if(cad.equalsIgnoreCase("short")) return true;
        else if(cad.equalsIgnoreCase("long")) return true;

            //con equalsIgnoreCase no nos importa si está en mayúsculas o minúsculas o alternadas
        else return false;
    }

}






