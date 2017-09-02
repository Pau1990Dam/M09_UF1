import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Scanner;

/**
 * P01_part2 Comproba l'autenticitat de la firma digital d'un fitxer amb la desencriptació y comprobació d'aquesta
 * a partir del arxiu que conté la signatura pública
 *
 */


public class P01_part2 {

    private static final String FITXER_FIRMAT = System.getProperty("user.dir")+"/SignaturaDigital/src/firmat.xml";

    private static final String PUBLIC_KEY_FILE = System.getProperty("user.dir")+"/SignaturaDigital/src/publicKey.pub";

    private static int SIGN_LENGTH = 128;

    public static void main(String[] args) {

        File pKey = new File(PUBLIC_KEY_FILE);
        File fitxerSignat = new File(FITXER_FIRMAT);

        /**
         * Indicamos si operaremos con una firma en formato hexadecimal (formato común) o directamente volcada desde
         * un stream de array de bytes.
         */

        isFirmaEnHex();

        /**Comprobamos que las vaiables globales y los archivos correspondientes (clave y archivo firmado) estén en su
        sitio y coincidan.
        */

        if(!PUBLIC_KEY_FILE.substring(PUBLIC_KEY_FILE.length()-4,PUBLIC_KEY_FILE.length()).equals(".pub")&&
                !pKey.isFile()&& !fitxerSignat.isFile()){
            System.out.println("No se ha encontrado ningún archivo de clave pública o fichero firmado a verificar" +
                    ". Compruebe las variables globales \"FITXER_FIRMAT\" y" + "\"PUBLIC_KEY_FILE\" y/o que los " +
                    "archivos *.pub y el firmado estén en la carpeta del programa.");
            return;
        }

        /**
         * Leemos la clave pública desde el archivo
         */

        ObjectInputStream inputStream = null;
        PublicKey pubKey = null;

        try {
            inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
            pubKey = (PublicKey) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Leemos los bytes del archivo firmado y con el método verificar leemos la firma de este y la desencriptamos
         */

        byte [] signaturaDesencriptada = null;
        byte [] fitxSignat = null;

        try {
            fitxSignat = Utils.read(fitxerSignat);
            signaturaDesencriptada = Utils.verificar(fitxSignat,pubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Recortamos la firma del archivo y calculamos el hash md5 del archivo
         */
        FileOutputStream ouput = null;
        byte [] HashContingutFitxSignat = null;


        try {
            File temp = File.createTempFile("tempfile",".tmp");
            ouput = new FileOutputStream(temp );
            ouput.write(Arrays.copyOfRange(fitxSignat,0,fitxSignat.length-SIGN_LENGTH));
            ouput.close();
            HashContingutFitxSignat = Utils.digestiona(temp ,"MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Por último comparamos si el hash que hemos calculado del contenido del archivo coincide con el hash firmado
         * , ya desencriptado, del mismo.
         */

        if(Arrays.equals(HashContingutFitxSignat,signaturaDesencriptada)){
            System.out.println("Fixer verificat!\nHash MD5 del fitxer : "+Utils.toHexString(HashContingutFitxSignat)
                    +"\nSignatura desencriptada : "+Utils.toHexString(signaturaDesencriptada));
        }else {
            System.out.println("El fitxer no s'ha pogut verificar!\n" +
                    "Hash MD5 del fitxer : "+Utils.toHexString(HashContingutFitxSignat)+"\nSignatura desencriptada : "+
                    Utils.toHexString(signaturaDesencriptada));
        }

    }

    public static void isFirmaEnHex(){

        Scanner entrada = new Scanner(System.in);
        String resposta;

        System.out.println("\tEstà la firma del fitxer a verificar en hexadecimal ?");
        System.out.println("\tRespongi S/N.");
        resposta  = entrada.next();
        if(!(resposta.equalsIgnoreCase("s")  || resposta.equalsIgnoreCase("n") ))
            main(new String[]{""});
        else{
            if(resposta.equalsIgnoreCase("s"))
               SIGN_LENGTH *=2;
            Utils.signatExpresatEnHex(resposta.equalsIgnoreCase("s"));
        }
    }
}
