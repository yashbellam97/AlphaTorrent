package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.MessageType;

public class OperationFactory {

    private static Operation choke = new Choke();
    private static Operation unChoke = new Unchoke();
    private static Operation bitfield = new Bitfield();
    private static Operation have = new Have();
    private static Operation interested = new Interested();
    private static Operation notInterested = new NotInterested();
    private static Operation piece = new Piece();
    private static Operation request = new Request();
    private static Operation handshake = new Handshake();

    public static Operation getOperation(MessageType messageType) {
        switch (messageType) {
            case CHOKE:
                return choke;
            case UNCHOKE:
                return unChoke;
            case BITFIELD:
                return bitfield;
            case HAVE:
                return have;
            case INTERESTED:
                return interested;
            case NOTINTERESTED:
                return notInterested;
            case PIECE:
                return piece;
            case REQUEST:
                return request;
            case HANDSHAKE:
                return handshake;
        }
        return null;
    }
}
