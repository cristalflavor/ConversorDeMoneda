import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String apiKey = "a592d58c691dd0a8415415ca/latest/";
        String apiDir = "https://v6.exchangerate-api.com/v6/";
        String[] base_code = {"USD", "ARS", "USD", "BRL", "USD", "COP"};
        String consulta = "";
        String conversion;

        System.out.println("---------------------------------------------");
        System.out.println("Bienvenido al conversor de monedas de ONE");
        double eleccion = 0;

        do{
            Scanner lectura = new Scanner(System.in);
            System.out.println("---------------------------------------------");
            System.out.println("Elija la opción deseada:");

            System.out.println(
                    "1) Dólar ------------> Peso Argentino\n" +
                            "2) Peso Argentino ---> Dólar\n" +
                            "3) Dólar ------------> Real Brasileño\n" +
                            "4) Real Brasileño ---> Dólar\n" +
                            "5) Dólar ------------> Peso Colombiano\n" +
                            "6) Peso Colombiano --> Dólar\n" +
                            "7) Salir"
            );

            System.out.println("---------------------------------------------");

            eleccion = lectura.nextDouble();

            if (eleccion == 7) {
                System.out.println("Gracias por utilizar nuestros servicios\n");
            } else if (eleccion > 0 && eleccion < 7){
                consulta = apiDir + apiKey + base_code[(int)(eleccion)-1];
                conversion = base_code[(int)(eleccion)];

                //Consulta a la API
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(consulta))
                        .build();

                HttpClient client = HttpClient.newHttpClient();

                HttpResponse<String> response = null;
                try {
                    response = client
                            .send(request, BodyHandlers.ofString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //Extraer conversión
                String json = response.body();
                String respuestaCodigo = "Algo malo sucedió";
                String respuestaConversion = "";
                int indiceCodigo = 3;
                int indiceConversion = 5;
                int indiceInicio = json.indexOf(base_code[(int)eleccion]);
                if ( indiceInicio != -1 ) {
                    int indiceComa = json.indexOf(",", indiceInicio);

                    int indiceLlave = json.indexOf("}", indiceInicio);

                    int indiceFin = ( indiceComa == -1 ) ? indiceLlave : indiceComa;
                    respuestaCodigo = json.substring(indiceInicio, indiceInicio + indiceCodigo);
                    respuestaConversion = json.substring(indiceInicio + indiceConversion, indiceFin);

                }

                System.out.println("Ingrese la cantidad de " + base_code[(int)(eleccion)-1] + " que desea convertir:");
                Double aConvertir = lectura.nextDouble();

                System.out.println("---------------------------------------------");
                System.out.println("Son " + base_code[(int)eleccion] + " " + Double.parseDouble(respuestaConversion) * aConvertir);
            } else {
                System.out.println("Por favor, ingrese una opción válida\n");
                System.out.println("---------------------------------------------");
            }




            //Les debo lo de Gson mis amorcitos!
            //Gson gson = new Gson();
            //Moneda moneda = new Moneda();
            //moneda = gson.fromJson(json, Moneda.class);
            //System.out.println(json);


        }while(eleccion != 7);

        System.out.println("Programa terminado");
        System.out.println("---------------------------------------------");




    }
}