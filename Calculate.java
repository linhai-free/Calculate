package Calculate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate {
    /*
    public static void main(String[] args) {
        System.out.println(calculate("x=1+2+3+4,y=1*2*3*4"));
        System.out.println(new Calculate("2*2*3.1415926"));
        System.out.println(new Calculate("(10+10)*10-10/10"));
        System.out.println(new Calculate("2^10"));
        System.out.println(new Calculate("10!"));
        System.out.println(new Calculate("(2*(3+2))!"));
    }
    */
    public static String calculate(String exp) {
        return new Calculate(exp).calculate();
    }
    private static String regex_num = "(\\d+(\\.\\d+)?)";
    private String exp;
    private String result;
    public Calculate(String exp) {
        this.exp = exp;
        this.result = exp.replaceAll("\\s+", "");
    }
    public String calculate() {
        calculateSub();
        calcuateFactorial();
        calcuate("\\^", Handler.Power);
        calcuate("\\*", Handler.Multiply);
        calcuate("/", Handler.Divide);
        calcuate("\\+", Handler.Add);
        calcuate("\\-", Handler.Subtract);
        return result;
    }
    private void calculateSub() {
        Pattern pattern = Pattern.compile("\\(([^\\(\\)]+)\\)");
        Matcher matcher = pattern.matcher(result);
        while (matcher.reset(result).find()) {
            updateResult(matcher, calculate(matcher.group(1)));
        }
    }
    private void calcuateFactorial() {
        Pattern pattern = Pattern.compile(regex_num + "!");
        Matcher matcher = pattern.matcher(result);
        while (matcher.reset(result).find()) {
            BigDecimal decimal = new BigDecimal(result.substring(0, result.length() - 1));
            if (decimal.toString().indexOf(".") > -1) {
                throw new RuntimeException("when a!, a must is a integer");
            }
            updateResult(matcher, factorial(decimal.intValue()));
        }
    }
    public static BigInteger factorial(int num) {
        if (num < 1) {
            throw new RuntimeException("num can't < 1");
        }
        if (num == 1) {
            return BigInteger.ONE;
        } else {
            BigInteger bigInteger = BigInteger.valueOf(num);
            return bigInteger.multiply(factorial(num - 1));
        }
    }
    private void calcuate(String regex_sign, Handler handler) {
        Pattern pattern = Pattern.compile(regex_num + regex_sign + regex_num);
        Matcher matcher = pattern.matcher(result);
        while (matcher.reset(result).find()) {
            String[] split = matcher.group().split(regex_sign);
            BigDecimal decimal = new BigDecimal(split[0]);
            BigDecimal decimal2 = new BigDecimal(split[1]);
            updateResult(matcher, handler.calculate(decimal, decimal2));
        }
    }
    private void updateResult(Matcher matcher, Object value) {
        result = result.substring(0, matcher.start()) + value + result.substring(matcher.end());
    }
    public String toString() {
        return exp + "=" + calculate();
    }
    public interface Handler {
        public BigDecimal calculate(BigDecimal decimal, BigDecimal decimal2);
        public static final Handler Add = new Handler() {
            public BigDecimal calculate(BigDecimal decimal, BigDecimal decimal2) {
                return decimal.add(decimal2);
            }
        };
        public static final Handler Subtract = new Handler() {
            public BigDecimal calculate(BigDecimal decimal, BigDecimal decimal2) {
                return decimal.subtract(decimal2);
            }
        };
        public static final Handler Multiply = new Handler() {
            public BigDecimal calculate(BigDecimal decimal, BigDecimal decimal2) {
                return decimal.multiply(decimal2);
            }
        };
        public static final Handler Divide = new Handler() {
            public BigDecimal calculate(BigDecimal decimal, BigDecimal decimal2) {
                if (decimal2.equals(BigDecimal.ZERO)) {
                    throw new RuntimeException("when a / b, b can't be zero");
                }
                return decimal.divide(decimal2);
            }
        };
        public static final Handler Power = new Handler() {
            public BigDecimal calculate(BigDecimal decimal, BigDecimal decimal2) {
                if (decimal2.toString().indexOf(".") > -1) {
                    throw new RuntimeException("when a ^ b, b must is a integer");
                }
                return decimal.pow(decimal2.intValue());
            }
        };
    }
}
