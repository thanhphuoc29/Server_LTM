package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.common.IProduct;
import rmi.common.Product;

public class ProductImpl extends UnicastRemoteObject implements IProduct{

    public ProductImpl() throws RemoteException {
    }

    @Override
    public Product getProduct(String code) throws RemoteException {
        return new Product(123, code, "PC", (float) 50.5, (float) 70.0, "true");
    }

    @Override
    public boolean insertProduct(Product product) throws RemoteException {
        if (product.name.length() <= 8 || product.name.length() >= 20 || product.exportPrice < product.importPrice){
            return false;
        }
        return true;
    }
    
}