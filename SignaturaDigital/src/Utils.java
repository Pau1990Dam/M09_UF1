import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;

/**
 * Created by pau on 28/12/16.
 */
public class Utils {

    private static final int keyLength = 1024;
    private static final String FITXER_CLAU_PRIVADA = "private.key";
    private static final String FITXER_CLAU_PUBLICA = "publicKey.pub";
    private static final String AsymetricAlgorithm = "RSA";
    private static boolean isSignInHex;

    /**
     * Indiquem si volem operar amb signatures expresades en bytes o hexadecimal, per saber si hem de fer conversions de
     * arrays de bytes a Strings que els representin en hexadecimal i viceversa
     * @param resposta
     */
    public static void signatExpresatEnHex(boolean resposta){
        isSignInHex = resposta;
    }

    public static boolean areKeysPresent(){

        return Files.exists(Paths.get(FITXER_CLAU_PRIVADA))&&Files.exists(Paths.get(FITXER_CLAU_PUBLICA));
    }

    public static KeyPair generateKey() throws NoSuchProviderException, NoSuchAlgorithmException, IOException {

        GenerateKeys keyGen = null;

        keyGen = new GenerateKeys(keyLength);
        keyGen.createKeys();

        //Creem els fitxers amb les claus
        keyGen.writeToFile(
                System.getProperty("user.dir")+"/SignaturaDigital/src/"+FITXER_CLAU_PUBLICA,
                keyGen.getPublicKey());

        keyGen.writeToFile(
                System.getProperty("user.dir")+"/SignaturaDigital/src/"+FITXER_CLAU_PRIVADA,
                keyGen.getPrivateKey());

        return keyGen.getKeyPair();
    }

    public static byte[] digestiona(File f, String algoritme) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance(algoritme);

        return  md.digest(read(f));
    }

    public static byte[] signar(byte [] text, PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher;
        cipher = Cipher.getInstance(AsymetricAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(text);

    }

    public static byte[] read(File file) throws IOException {

        return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
    }

    public static byte[] concatenateByteArrays(byte [] text, byte [] encriptedHash){

        byte [] signature = null;

        if(isSignInHex)
            signature = toHexString(encriptedHash).getBytes();
        else
            signature = encriptedHash;

        byte [] concatenatedArray = new byte[text.length + signature.length];

        System.arraycopy(text, 0, concatenatedArray, 0, text.length);
        System.arraycopy(signature, 0, concatenatedArray, text.length, signature.length);

        return concatenatedArray;
    }

    public static void write(String f, byte[] byteArray) throws IOException {

        FileOutputStream fos = null;

        fos = new FileOutputStream(f);
        fos.flush();
        fos.write(byteArray);
        fos.close();
    }

    public static byte[] verificar(byte[] text, PublicKey key) throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {

        byte [] signature = null;

        if(isSignInHex){
            signature = Arrays.copyOfRange(text,text.length-(keyLength/4),text.length);
            signature = hexStringToByteArray(new String(signature));
        }else
            signature = Arrays.copyOfRange(text,text.length-(keyLength/8),text.length);

        final Cipher cipher = Cipher.getInstance(AsymetricAlgorithm);

        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(signature);
    }

    /**
     * Transforma los valores de un array de bytes en String con los valores equivalentes en hexadecimal
     * @param bytes
     * @return
     */
    public static String toHexString(byte[] bytes) {

        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * Transforma el valor de un String en hexadecimal en un array de bytes q no es mismo q string.getBytes()
     * @param s
     * @return
     */
    public static byte[] hexStringToByteArray(String s) {

        int len = s.length();

        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
