package AlphaTorrent.messages.action;

import AlphaTorrent.messages.dto.MessageType;

public class OperationFactory {

    private Operation choke = new Choke();
    private Operation unChoke = new Unchoke();
    private Operation bitfield = new Bitfield();
    private Operation have = new Have();
    private Operation interested = new Interested();
    private Operation notInterested = new NotInterested();
    private Operation piece = new Piece();
    private Operation request = new Request();

    public Operation getOperation(MessageType messageType) {
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
        }
        return null;
    }
}
