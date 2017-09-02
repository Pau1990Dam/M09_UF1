import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Lector {
    /**
     * Devuelve en un String los carácteres leídos de un fichero de texto plano pasado por parámetro.
     * @param archivo
     * @return
     * @throws IOException
     */
    public static String getContenido(File archivo) throws IOException {

        BufferedReader lector = new BufferedReader( new FileReader(archivo));
        StringBuilder texto = new StringBuilder();
        String str = lector.readLine();

        while(str!=null){
            texto.append(str);
            str = lector.readLine();
        }
        return texto.toString();
    }
}
