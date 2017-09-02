import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by pau on 27/10/16.
 */
public class Escritor {
    /**
     * Escribe el String pasado por parámetro en el directorio indicado, también por parámetro, con el título distintivo
     * cesarClave- 'valor de la clave' con la que se desencriptó el texto, pasado también por parámetro.
     * @param directorio Directorio donde alojar el resultado.
     * @param txt   String desencriptado que se escribirá en un fichero
     * @param clave Sufijo que se adjuntará al título del arhivo creado para distinguirlo
     * @throws IOException
     */
    public static void setEscribir(File directorio, String txt, int clave) throws IOException {
        File salida = new File(directorio,"cesarClave-"+clave);
        BufferedWriter escritor = new BufferedWriter (new FileWriter(salida));
        escritor.write(txt);
        escritor.close();
    }
}
