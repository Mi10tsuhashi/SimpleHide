package com.mi10n.SimpleHide.util;
import java.nio.ByteBuffer;

public class UUID {
  public static byte[] UUIDtoByte(java.util.UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }
  public static java.util.UUID BytetoUUID(byte[] bytearray) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(bytearray);
    long high = byteBuffer.getLong();
    long low = byteBuffer.getLong();
    return new java.util.UUID(high, low);
  }
}
