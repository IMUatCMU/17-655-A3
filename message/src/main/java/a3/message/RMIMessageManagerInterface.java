package a3.message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * NOTE: This class is directly ported from the application package. Code format updated.
 *
 * This class is the interface definition for the event manager services that are available to
 * participants
 */
public interface RMIMessageManagerInterface extends Remote {

    long Register() throws RemoteException;

    void UnRegister(long SenderID) throws RemoteException;

    void SendMessage(Message m) throws RemoteException;

    MessageQueue GetMessageQueue(long SenderID) throws RemoteException;
}
