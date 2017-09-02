import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by pau on 27/10/16.
 */
public class Main {

    static Scanner entrada;

    public static void main(String[] args) {

        entrada = new Scanner(System.in);
        int opcion;
        String cesar = "Twzmu Qxacu \"Vmycm xwzzw ycqayciu mab ycq lwtwzmu qxacu ycqi lwtwz aqb iumb, kwvamkbmbcz, ilqxqakq dmtqb...\" \"Vw pig vilqm ycm ium mt lwtwz uqauw, ycm tw jcaycm, tw mvkcmvbzm g tw ycqmzi, aquxtmumvbm xwzycm ma mt lwtwz.\" Twzmu qxacu lwtwz aqb iumb, kwvamkbmbcz ilqxqakqvo mtqb. Cb xpizmbzi xczca mc tmkbca tiwzmmb, mb nickqjca czvi bmuxwz. Ycqaycm dwtcbxib vqjp ib lcq zcbzcu twjwzbqa ib ycqa uiczqa. Ycqaycm qv amu cb mzib mtmumvbcu jqjmvlcu i aqb iumb vqat. Xzimamvb quxmzlqmb, izkc vmk bqvkqlcvb bmuxca, ivbm mzib mnnqkqbcz wzkq, aml bmuxca tqjmzw uiovi vmk izkc. Viu nickqjca vqjp lqiu, vmk kwvamkbmbcz vmycm ctbzqkma ik. Kzia xctdqviz tikqvqi xwzbi. Viu mtmumvbcu mab vmk izkc aioqbbqa, mc ozidqli vqat zpwvkca. Xzwqv cb vqjp ycqa mvqu uwttqa akmtmzqaycm. Qvbmzlcu mb uitmacili niuma ik ivbm qxacu xzquqa qv nickqjca. Qvbmomz wzvizm twzmu momb lwtwz bmuxca, dmt dmabqjctcu aixqmv lqkbcu. Vcttiu dmpqkcti ib izkc momb ozidqli. Cb xpizmbzi xczca mb dizqca mtmumvbcu. Lwvmk vmk mcqauwl ivbm, aml bqvkqlcvb aixqmv. Viu uiaai uiaai, awttqkqbclqv ycqa czvi i, uwttqa jqjmvlcu uq. Aml vmk rcabw qv ivbm lixqjca twjwzbqa nikqtqaqa ib bwzbwz. ";
        File directorioResultados;
        File archivoAdesencriptar;

        do{
            System.out.println("\t\t------------------------");
            System.out.println("\t\t||DESENCRIPTADOR CESAR||");
            System.out.println("\t\t------------------------");
            System.out.println();
            System.out.println("ELIGE UNA OPCIÓN : ");
            System.out.println("\t1. Automático. (El programa desencripta un String con el texto del ejercicio y lo" +
                    "guarda en la carpeta del propio programa)");
            System.out.println("\t2. Manual. Has de poner la ruta del archivo a desencriptar e indicar la carpeta donde" +
                    " se alojarán los resultados.");
            System.out.println("\t3. Salir. Apreta qualquier otra tecla.");
            System.out.println();
            System.out.println("Opcion -> "+ ( opcion = Integer.parseInt(entrada.nextLine()) )  );

            switch(opcion){
                case 1:

                    directorioResultados = new File(System.getProperty("user.dir")+"/Cesar/");// System.getProperty("user.dir") directorioResultados = new File(new File("").getAbsolutePath())
                    Main.startDecryptor(directorioResultados, cesar);
                    break;

                case 2:

                    //Establecer la ruta del archivo a desencriptar
                    do {

                        System.out.println("Indique la ruta del archivo que quiere desencriptar : ");
                        archivoAdesencriptar = new File(entrada.nextLine());

                        if( !archivoAdesencriptar.isFile() ){
                            System.out.println("Error : "+archivoAdesencriptar.getAbsolutePath()+" no es válido.");
                            continue;
                        }

                        try {
                            cesar = Lector.getContenido(archivoAdesencriptar);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }while(false);

                    //Establecer la ruta donde alojar los ficheros con los resultados
                    do{
                        System.out.println("Por último indique la ruta del directorio donde quiere alojar los resultados");
                        directorioResultados = new File(entrada.nextLine());

                        if(!directorioResultados.isDirectory()){
                            System.out.println("Error : "+directorioResultados.getAbsolutePath()+" no es válido.");
                            continue;
                        }

                        Main.startDecryptor(directorioResultados, cesar);

                    }while(false);

                    break;
                default:
                    System.out.println("ADEU!!!!");
                    break;
            }

        }while(opcion == 1 ||  opcion == 2);

    }

    /**
     * Esta función invoca la función static del objeto Desencriptador tantas veces como letras hay en el abecedario
     * inglés +1 (para corroborar que ha dado la vuelta) con los parámetros pasados más el parámetro int "clave"
     * cuyos valores son autogenerado por el for.
     * @param directorioResultados Directorio dónde se alojarán los ficheros desencriptados
     * @param cesar String a desencriptar
     */
    public static void startDecryptor(File directorioResultados, String cesar){
        try {
            for(int clave = 0 ; clave < 27 ; clave++)
                // El Escritor escribe cada resultado del texto desencriptado con una clave en un archivo identificado x
                // el título "cesarClave-'clave utilizada'" que se encuentra el el direcorio pasado por el parámetro
                // "directoriResultado"
                Escritor.setEscribir(directorioResultados, Desencriptador.getCodigo(
                        cesar ,clave),clave);
            System.out.println("Los resultados se encuentran en el directorio: "+directorioResultados.getAbsolutePath());
            System.out.println("Apreta intro para volver al menú: ");
            entrada.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
