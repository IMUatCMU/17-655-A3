package a3.message;

import java.io.Serializable;

/**
 * NOTE: This class is directly ported from the application package. Code format updated.
 *
 * This class defines messages. Messages include the sender's ID, the message ID, and
 * a message field for sending small messages between entities participating in
 * the message system.
 */
public class Message implements Serializable {

    private String MessageText;
    private int MessageId;
    private long SenderId;

    public Message(int MsgId, String Text) {
        this.MessageText = Text;
        this.MessageId = MsgId;
    }

    public Message(int MsgId) {
        this.MessageText = null;
        this.MessageId = MsgId;
    }

    public long GetSenderId() {
        return this.SenderId;
    }

    public void SetSenderId(long id) {
        this.SenderId = id;
    }

    public int GetMessageId() {
        return this.MessageId;
    }

    public String GetMessage() {
        return this.MessageText;
    }
}
