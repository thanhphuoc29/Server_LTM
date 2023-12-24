package rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer {
    public static void main(String[] args) {
        try {
            ProductImpl product = new ProductImpl();
            LocateRegistry.createRegistry(1234);
            Naming.bind("rmi://localhost:1234/product", product);
            System.out.println("============ RMI Server Started ============");
        } catch (RemoteException | AlreadyBoundException | MalformedURLException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}