package a3.message;

import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * NOTE: This class is directly ported from the application package. Code format updated.
 *
 * This class provides an interface to the message manager for participants (processes), enabling
 * them to to send and receive messages between participants. A participant is any thread, object,
 * or process, that instantiates an MessageManagerInterface object - this automatically attempts to
 * register that entity with the message manager
 */
public class MessageManagerInterface {

    private long ParticipantId = -1;
    private RMIMessageManagerInterface em = null;
    private String DEFAULTPORT = "1099";

    class SendMessageException extends Exception {
        SendMessageException() {
            super();
        }

        SendMessageException(String s) {
            super(s);
        }

    } // Exception

    class GetMessageException extends Exception {
        GetMessageException() {
            super();
        }

        GetMessageException(String s) {
            super(s);
        }

    } // Exception

    class ParticipantAlreadyRegisteredException extends Exception {
        ParticipantAlreadyRegisteredException() {
            super();
        }

        ParticipantAlreadyRegisteredException(String s) {
            super(s);
        }

    } // Exception

    class ParticipantNotRegisteredException extends Exception {
        ParticipantNotRegisteredException() {
            super();
        }

        ParticipantNotRegisteredException(String s) {
            super(s);
        }

    } // Exception

    class LocatingMessageManagerException extends Exception {
        LocatingMessageManagerException() {
            super();
        }

        LocatingMessageManagerException(String s) {
            super(s);
        }

    } // Exception

    class LocalHostIpAddressException extends Exception {
        LocalHostIpAddressException() {
            super();
        }

        LocalHostIpAddressException(String s) {
            super(s);
        }

    } // Exception

    class RegistrationException extends Exception {
        RegistrationException() {
            super();
        }

        RegistrationException(String s) {
            super(s);
        }

    }

    public MessageManagerInterface() throws LocatingMessageManagerException, RegistrationException, ParticipantAlreadyRegisteredException {
        if (ParticipantId == -1) {
            try {
                em = (RMIMessageManagerInterface) Naming.lookup("MessageManager");
            } catch (Exception e) {
                throw new LocatingMessageManagerException("Message manager not found on local machine at default port (1099)");
            }

            try {
                ParticipantId = em.Register();
            } catch (Exception e) {
                throw new RegistrationException("Error registering participant " + ParticipantId);
            }
        } else {
            throw new ParticipantAlreadyRegisteredException("Participant already registered " + ParticipantId);
        }
    }

    public MessageManagerInterface(String ServerIpAddress) throws LocatingMessageManagerException, RegistrationException, ParticipantAlreadyRegisteredException {
        String EMServer = "//" + ServerIpAddress + ":" + DEFAULTPORT + "/MessageManager";
        if (ParticipantId == -1) {
            try {
                em = (RMIMessageManagerInterface) Naming.lookup(EMServer);
            } catch (Exception e) {
                throw new LocatingMessageManagerException("Message manager not found on machine at:" + ServerIpAddress + "::" + e);
            }

            try {
                ParticipantId = em.Register();
            } catch (Exception e) {
                throw new RegistrationException("Error registering participant " + ParticipantId);
            }
        } else {
            throw new ParticipantAlreadyRegisteredException("Participant already registered " + ParticipantId);
        }
    }

    public long GetMyId() throws ParticipantNotRegisteredException {
        if (ParticipantId != -1) {
            return ParticipantId;
        } else {
            throw new ParticipantNotRegisteredException("Participant not registered");
        }
    }

    public String GetRegistrationTime() throws ParticipantNotRegisteredException {
        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
        if (ParticipantId != -1) {
            TimeStamp.setTimeInMillis(ParticipantId);
            return (TimeStampFormat.format(TimeStamp.getTime()));
        } else {
            throw new ParticipantNotRegisteredException("Participant not registered");
        }
    }

    public void SendMessage(Message evt) throws ParticipantNotRegisteredException, SendMessageException {
        if (ParticipantId != -1) {
            try {
                evt.SetSenderId(ParticipantId);
                em.SendMessage(evt);
            } catch (Exception e) {
                throw new SendMessageException("Error sending message" + e);
            }
        } else {
            throw new ParticipantNotRegisteredException("Participant not registered");
        }
    }

    public MessageQueue GetMessageQueue() throws ParticipantNotRegisteredException, GetMessageException {
        MessageQueue eq = null;
        if (ParticipantId != -1) {
            try {
                eq = em.GetMessageQueue(ParticipantId);
            } catch (Exception e) {
                throw new GetMessageException("Error getting message" + e);
            }
        } else {
            throw new ParticipantNotRegisteredException("Participant not registered");
        }
        return eq;
    }

    public void UnRegister() throws ParticipantNotRegisteredException, RegistrationException {
        if (ParticipantId != -1) {
            try {
                em.UnRegister(ParticipantId);
            } catch (Exception e) {
                throw new RegistrationException("Error unregistering" + e);
            }
        } else {
            throw new ParticipantNotRegisteredException("Participant not registered");
        }
    }
}
