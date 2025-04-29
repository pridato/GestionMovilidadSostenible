package es.uned.utils;

import es.uned.model.Coordenadas;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeolocalizacionPorIP {

    /**
     * Método para obtener las coordenadas de la geolocalización por IP.
     * @return Coordenadas con la longitud y latitud.
     */
    public static Coordenadas obtenerCoordenadasDesdeIP() {
        try {
            // obtenemos el string completo
            String respuesta = geoLocalizacionPorIP();
            if (respuesta == null || respuesta.isEmpty()) return null;

            // extraemos latitud y longitud como substrings
            double lat = extraerDouble(respuesta, "\"lat\":");
            double lon = extraerDouble(respuesta, "\"lon\":");

            // lon = x, lat = y
            return new Coordenadas(lon, lat);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para extraer un valor double de un string JSON.
     *
     * @param texto texto JSON
     * @param clave clave a buscar
     * @return valor double extraído
     */
    private static double extraerDouble(String texto, String clave) {
        // buscamos la clave en el string, si no existe devolvemos 0.0
        int inicio = texto.indexOf(clave);
        if (inicio == -1) return 0.0;

        // buscamos el valor asociado a la clave
        inicio += clave.length();
        int fin = texto.indexOf(",", inicio);

        if (fin == -1) fin = texto.length();

        // extraemos el valor y lo convertimos a double
        String valorStr = texto.substring(inicio, fin).trim();
        return Double.parseDouble(valorStr);
    }


    /**
     * Método para obtener la geolocalización por IP.
     * @return JSON con la información de geolocalización.
     */
    private static String geoLocalizacionPorIP() {
        try {
            // configuramos la URL de la API y el método de conexión
            URL url = new URL("http://ip-api.com/json/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // configuramos los headers de la conexión
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // leemos la respuesta de la API
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // cerramos el BufferedReader y desconectamos
            in.close();
            con.disconnect();

           return content.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
