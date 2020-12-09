package AlphaTorrent.utility;

public class ByteArrayExt
{
    public static byte[] setBit(byte[] self, int index, boolean value)
    {
        int byteIndex = index / 8;
        int bitIndex = index % 8;
        byte mask = (byte)(1 << bitIndex);

        self[byteIndex] = (byte)(value ? (self[byteIndex] | mask) : (self[byteIndex] & ~mask));
        return self;
    }

    public static boolean getBit(byte[] self, int index)
    {
        int byteIndex = index / 8;
        int bitIndex = index % 8;
        byte mask = (byte)(1 << bitIndex);

        return (self[byteIndex] & mask) != 0;
    }
}
