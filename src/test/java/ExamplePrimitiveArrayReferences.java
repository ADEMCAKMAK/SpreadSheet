import java.io.Serializable;

public class ExamplePrimitiveArrayReferences implements Serializable {

    private static final long serialVersionUID = 305925496300707027L;
    // Integer
    byte[] byteValue;
    short[] shortValue;
    int[] intValue;
    long[] longValue;

    // Real
    float[] floatValue;
    double[] doubleValue;

    // Character
    char[] charValue;

    // Boolean
    boolean[] booleanValue;

    public ExamplePrimitiveArrayReferences() {
    }

    public ExamplePrimitiveArrayReferences(byte[] byteValue, short[] shortValue, int[] intValue, long[] longValue,
                                           float[] floatValue, double[] doubleValue,
                                           char[] charValue, boolean[] booleanValue) {
        this.byteValue = byteValue;
        this.shortValue = shortValue;
        this.intValue = intValue;
        this.longValue = longValue;
        this.floatValue = floatValue;
        this.doubleValue = doubleValue;
        this.charValue = charValue;
        this.booleanValue = booleanValue;
    }

    public byte[] getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte[] byteValue) {
        this.byteValue = byteValue;
    }

    public short[] getShortValue() {
        return shortValue;
    }

    public void setShortValue(short[] shortValue) {
        this.shortValue = shortValue;
    }

    public int[] getIntValue() {
        return intValue;
    }

    public void setIntValue(int[] intValue) {
        this.intValue = intValue;
    }

    public long[] getLongValue() {
        return longValue;
    }

    public void setLongValue(long[] longValue) {
        this.longValue = longValue;
    }

    public float[] getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float[] floatValue) {
        this.floatValue = floatValue;
    }

    public double[] getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double[] doubleValue) {
        this.doubleValue = doubleValue;
    }

    public char[] getCharValue() {
        return charValue;
    }

    public void setCharValue(char[] charValue) {
        this.charValue = charValue;
    }

    public boolean[] getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean[] booleanValue) {
        this.booleanValue = booleanValue;
    }

}
