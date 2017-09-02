/**
 * Created by pau on 27/10/16.
 */
public class Desencriptador {
    /**
     * Devuelve un String creado a partir del String pasado por parámetro, cambiando el valor de los carácteres
     * alfabéticos de este por los correspondientes que estén en el abecedario a tantos desplazamientos hacia la
     * izquierda indicados por el parámetro entero.
     * @param txt String referencia desde el que se creará un homólogo con el valor de los carácteres correspondientes
     *            al desplazamiento indicado por el parametro entero "desplazamiento"
     * @param desplazamiento  Entero que indica el valor a la izquierda que tiene que tomar cada carácter alfabético
     *                        respecto al caracter alfabético tomado como referencia en el String pasado por parámetro
     * @return
     */
    public static String getCodigo(String txt, int desplazamiento){

        StringBuilder result = new StringBuilder();
        char caracterActual;
        int desplazador;
        int upperCaser = 0;

        for(int i=0;i<txt.length();i++){

            caracterActual = txt.charAt(i);

            if( ( (caracterActual  >= 'a') && (caracterActual<= 'z') ) ||
                    ( (caracterActual >= 'A') && (caracterActual <= 'Z') ) ){
                //Si el caracter se corresponde con una mayúscula el int upperCaser almacenará 32 desplazamientos xa q
                //cuando este sea desencriptado sea el carácter original en mayúscula
                if(caracterActual < 97) upperCaser = 32;

                desplazador = ( (caracterActual - desplazamiento) - 96 ) + upperCaser;

                if(desplazador <= 0){
                    result.append( (char) ( ( ( 122 - (desplazador * (-1)) %27) ) - upperCaser ) );
                    upperCaser = 0;
                    continue;
                }else {
                    result.append( (char) ( ( ( (desplazador % 27) + 96) ) - upperCaser ) );
                    upperCaser = 0;
                    continue;
                }
            }
            result.append(caracterActual);
        }
        return result.toString();
    }
}
