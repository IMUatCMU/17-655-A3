package a3.message;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * NOTE: This class is directly ported from the application package. Code format updated.
 *
 * This class is the message manager responsible for receiving and distributing messages from
 * participants and all associated house keeping chores. Communication with participants is via RMI.
 * There are a number of RMI methods that allow participants to register, post messages, get
 * messages,
 */
public class MessageManager extends UnicastRemoteObject implements RMIMessageManagerInterface {

    static Vector<MessageQueue> MessageQueueList;
    static RequestLogger l;

    public static void main(String args[]) {
        try {
            LocateRegistry.createRegistry(1099);

            InetAddress LocalHostAddress = InetAddress.getLocalHost();
            String MessageManagerIpAddress = LocalHostAddress.getHostAddress();

            MessageManager em = new MessageManager();
            Naming.bind("MessageManager", em);

            l.DisplayStatistics("Server IP address::" + MessageManagerIpAddress + ". Message manager ready.");
        } catch (Exception e) {
            l.DisplayStatistics("Message manager startup error: " + e);
        }
    }

    public MessageManager() throws RemoteException {
        super();
        l = new RequestLogger();
        MessageQueueList = new Vector<>(15, 1);
    }

    @Override
    synchronized public long Register() throws RemoteException {
        MessageQueue mq = new MessageQueue();
        MessageQueueList.add(mq);
        l.DisplayStatistics("Register message. Issued ID = " + mq.GetId());
        return mq.GetId();
    }

    @Override
    synchronized public void UnRegister(long SenderID) throws RemoteException {
        MessageQueue mq;
        boolean found = false;

        for (int i = 0; i < MessageQueueList.size(); i++) {
            mq = MessageQueueList.get(i);
            if (mq.GetId() == SenderID) {
                MessageQueueList.remove(i);
                found = true;
            }
        }

        if (found)
            l.DisplayStatistics("Unregistered ID::" + SenderID);
        else
            l.DisplayStatistics("Unregister error. ID:" + SenderID + " not found.");
    }

    @Override
    synchronized public void SendMessage(Message m) throws RemoteException {
        MessageQueue mq;

        for (int i = 0; i < MessageQueueList.size(); i++) {
            mq = MessageQueueList.get(i);
            mq.AddMessage(m);
            MessageQueueList.set(i, mq);
        }

        l.DisplayStatistics("Incoming message posted from ID: " + m.GetSenderId());
    }

    @Override
    synchronized public MessageQueue GetMessageQueue(long SenderID) throws RemoteException {
        MessageQueue mq, temp = null;
        boolean found = false;

        for (int i = 0; i < MessageQueueList.size(); i++) {
            mq = MessageQueueList.get(i);

            if (mq.GetId() == SenderID) {
                mq = MessageQueueList.get(i);
                temp = mq.GetCopy();
                mq.ClearMessageQueue();
                found = true;
            }
        }

        if (found)
            l.DisplayStatistics("Get message queue request from ID: " + SenderID + ". Message queue returned.");
        else
            l.DisplayStatistics("Get message queue request from ID: " + SenderID + ". ID not found.");

        return temp;
    }

    private class RequestLogger {
        int RequestsServiced = 0;    // This is the number of requests seviced

        void DisplayStatistics(String message) {
            RequestsServiced++;

            if (message.length() == 0) {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Number of requests: " + RequestsServiced);
                System.out.println("Number of registered participants: " + MessageQueueList.size());
                System.out.println("-------------------------------------------------------------------------------");
            } else {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Message:: " + message);
                System.out.println("Number of requests: " + RequestsServiced);
                System.out.println("Number of registered participants: " + MessageQueueList.size());
                System.out.println("-------------------------------------------------------------------------------");
            }
        }
    }
}
