package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IProduct extends Remote{
    public Product getProduct (String code) throws RemoteException;
    public boolean insertProduct(Product product) throws RemoteException;
}