import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ExampleJavaMathReferences implements Serializable {

    private static final long serialVersionUID = 2339974159216002085L;

    private BigDecimal bigDecimal;
    private BigInteger bigInteger;

    public ExampleJavaMathReferences() {
    }

    public ExampleJavaMathReferences(BigDecimal bigDecimal, BigInteger bigInteger) {
        this.bigDecimal = bigDecimal;
        this.bigInteger = bigInteger;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }
}
