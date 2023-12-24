package rmi.client;

import rmi.common.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ann52
 */
public class RMIClient {
    public static void main(String[] args) {
        try {
            IProduct productControl = (IProduct) Naming.lookup("rmi://localhost:1234/product");
            Product product = productControl.getProduct("B20DCCN650");
            System.out.println(product);
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            float importPrice = scanner.nextFloat();
            float exportPrice = scanner.nextFloat();
            product.name = name;
            product.exportPrice = exportPrice;
            product.importPrice = importPrice;
            boolean state = productControl.insertProduct(product);
            System.out.println(state);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}