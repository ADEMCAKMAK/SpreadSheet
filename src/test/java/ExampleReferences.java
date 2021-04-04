import java.io.Serializable;

public class ExampleReferences implements Serializable {

    private static final long serialVersionUID = 8846452301980926636L;
    // Boolean
    Boolean booleanValue;

    // Number
    Byte byteValue;
    Short shortValue;
    Double doubleValue;
    Float floatValue;
    Integer integer;
    Long longValue;

    // Character
    Character characterValue;
    String stringValue;

    public ExampleReferences() {
    }

    public ExampleReferences(Boolean booleanValue,
                             Byte byteValue, Short shortValue, Double doubleValue, Float floatValue, Integer integer, Long longValue,
                             Character characterValue, String stringValue) {
        this.booleanValue = booleanValue;
        this.byteValue = byteValue;
        this.shortValue = shortValue;
        this.doubleValue = doubleValue;
        this.floatValue = floatValue;
        this.integer = integer;
        this.longValue = longValue;
        this.characterValue = characterValue;
        this.stringValue = stringValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(Byte byteValue) {
        this.byteValue = byteValue;
    }

    public Short getShortValue() {
        return shortValue;
    }

    public void setShortValue(Short shortValue) {
        this.shortValue = shortValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Character getCharacterValue() {
        return characterValue;
    }

    public void setCharacterValue(Character characterValue) {
        this.characterValue = characterValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
