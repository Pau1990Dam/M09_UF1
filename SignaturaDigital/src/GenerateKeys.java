import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;


/**
 * Generador de claus asimètriques
 */
public class GenerateKeys {

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String Algorithm = "RSA";

    /**
     * Inicializa el generador para crear claves del tipo y longitud indicadas por paràmetro (estàn en forma de variable
     * global para no crear código extra y facilitar tanto la elaboración como corrección del ejercicio)
     * @param keylength
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance(Algorithm);
        this.keyGen.initialize(keylength);
    }

    /**
     * Instanciamos los objectos que contendràn la pareja de claves creadas desde el generador de claves ya instanciado
     */
    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    /**
     * Devuelve un objeto de tipo KeyPair des de el qual se puede obtener toda la info relativa a las claves generadas
     * inclusive las mismas claves
     */
    public KeyPair getKeyPair(){ return pair; }

    /**
     * @return PrivateKey
     */
    public PrivateKey getPrivateKey() { return privateKey; }

    /**
     * @return PublicKey
     */
    public PublicKey getPublicKey() { return publicKey; }

    /**
     * Escribe el objeto clave pasado por paràmetro en el fichero indicado desde el paràmetro String
     * @param path
     * @param key
     * @throws IOException
     */
    public void writeToFile(String path, Key key) throws IOException {
        File f = new File(path);

        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(key);
        oos.flush();
        oos.close();
    }

}