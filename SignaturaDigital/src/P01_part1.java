import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;
import java.util.Scanner;

/**
 * P01_part1 crea un fitxer firmat a partir d'un fitxer plà amb una clau privada passada o autogenerada.
 */

public class P01_part1 {

    public static final String PRIVATE_KEY_FILE = System.getProperty("user.dir")+"/SignaturaDigital/src/private.key";

    public static final String FITXER_PLA = System.getProperty("user.dir")+"/SignaturaDigital/src/planets.xml";
    public static final String FITXER_SIGNAT = System.getProperty("user.dir")+"/SignaturaDigital/src/firmat.xml";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException, NoSuchProviderException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {

        KeyPair keyPair = null;
        PrivateKey prik = null;

        File f = new File(FITXER_PLA);

        /**
         * Indicamos si crearemos una firma en formato hexadecimal (formato común, ej md5) o directamente volcada desde
         * un stream de array de bytes.
         */

        activarHex();

        /**
         * Final.Utils areKeysPresents:
         * comprueba que existen los ficheros de la clave pública
         * y clave privada
         */

        if (!Utils.areKeysPresent()) {

            /**
             * Si no existe, las general las guarda en el fichero
             * y devuelve el par. Luego nos quedamos con la pública
             */

            keyPair = Utils.generateKey();
            prik = keyPair.getPrivate();
        } else {

            /**
             * Si existen las claves, las leemos del fichero
             * y las guardamos en una variable
             */


            ObjectInputStream inputStream = null;
            inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
            prik = (PrivateKey) inputStream.readObject();
        }


        /**
         * Aquí llegamos conlas claves privadas.
         * Final.Utils digestiona coge el fichero f,
         * y devuelve el array de bits correspondiente al
         * hash del fichero en MD5
         */
        byte[] digestionat = Utils.digestiona(f, "MD5");

        /**
         * en signar, cogemos la clave privada y el hash
         * y encriptamos el hash
         */
        byte[] encryptdigestionat = Utils.signar(digestionat, prik);

        /**
         * read: passa el fichero a bytes
         * concatenateByteArrays: añadimos al final del fichero los bytes de la firma
         * write: vuelve a guardar los bytes en fichero.
         * **/

        Utils.write(FITXER_SIGNAT, Utils.concatenateByteArrays(Utils.read(f), encryptdigestionat));

    }

    public static void activarHex() throws IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchProviderException, InvalidKeyException, ClassNotFoundException {

        Scanner entrada = new Scanner(System.in);
        String resposta;
        System.out.println("\tVol representar la signatura en una elegant cadena en hexadecimal en comptes de volcar" +
                " directament els bytes de la signatura sobre el fitxer? ");
        System.out.println("\tRespongi S/N.");
        resposta  = entrada.next();
        if(!(resposta.equalsIgnoreCase("s") || resposta.equalsIgnoreCase("n") ))
            main(new String[]{""});
        else{
            Utils.signatExpresatEnHex(resposta.equalsIgnoreCase("s"));
        }
    }
}
