package a3.message;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

/**
 * NOTE: This class is directly ported from the application package. Code format updated.
 *
 * This class defines message queues which are stored by the MessageManger. Each registered participant
 * has an message queue assigned to them. As events are sent by registered participants to the MessageManger
 * they are posted in each queue. Queues are removed when participants unregister.
 */
public class MessageQueue implements Serializable {

    private Vector<Message> MessageList;
    private long QueueId;
    private	int ListSize;

    public MessageQueue() {
        this.MessageList = new Vector<>(15, 1);
        Calendar TimeStamp = Calendar.getInstance();
        this.QueueId = TimeStamp.getTimeInMillis();
        this.ListSize = 0;
    }

    public long GetId() {
        return this.QueueId;
    }

    public int GetSize() {
        return this.MessageList.size();
    }

    public void AddMessage(Message m) {
        this.MessageList.add(m);
    }

    public Message GetMessage() {
        Message m = null;

        if (MessageList.size() > 0) {
            m = this.MessageList.get(0);
            this.MessageList.removeElementAt(0);
        }

        return m;
    }

    public void ClearMessageQueue() {
        this.MessageList.removeAllElements();
    }

    @SuppressWarnings("unchecked")
    public MessageQueue GetCopy() {
        MessageQueue mq = new MessageQueue();
        mq.QueueId = QueueId;
        mq.MessageList = (Vector<Message>) MessageList.clone();
        return mq ;
    }
}
