/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp.servidorcalculadora;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author pedro argibay
 */
public class PSPServidorCalculadora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            System.out.println("Creando CalculatorServer");

            ServerSocket serverSocket = new ServerSocket();

            System.out.println("Realizando el bind");

            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            serverSocket.bind(addr);

            System.out.println("Aceptando la conexion con el ClienteSocket");

            Socket newSocket = serverSocket.accept();

            System.out.println("Conexión establecida");

            InputStream is = newSocket.getInputStream();
            OutputStream os = newSocket.getOutputStream();

            String[] aux2 = new String[3];
            byte[] mensaje;
            Double proba;
            do {
                mensaje = new byte[25];
                is.read(mensaje);
                String aux1 = new String(mensaje).trim();
                System.out.println(aux1);

                aux2 = aux1.split(",");
                proba = calculo(aux2);

                System.out.println("Mensaje: " + proba);
                String aux3 = "";
                aux3 = aux3 + proba;
                os.write(proba.toString().getBytes());
            } while (proba != -901);

            System.out.println("Cerrando socket");

            newSocket.close();

            System.out.println("Cerrando CalculatorSocket");

            serverSocket.close();

            System.out.println("Terminado con éxito");

        } catch (IOException e) {
        }

    }

    //metodos para realizar los calculos
    public static double calculo(String[] operacion) {
        switch (operacion[0]) {
            case "+":
                return Double.parseDouble(operacion[1]) + Double.parseDouble(operacion[2]);
            case "-":
                return Double.parseDouble(operacion[1]) - Double.parseDouble(operacion[2]);
            case "*":
                return Double.parseDouble(operacion[1]) * Double.parseDouble(operacion[2]);
            case "/":
                return division(Double.parseDouble(operacion[1]), Double.parseDouble(operacion[2]));

            case "end":
                return -901;
        }
        return -1;
    }

    public static double division(double n1, double n2) {
        if (n2 == 0) {
            return 0.0;
        } else {
            return n1 / n2;
        }

    }

}
