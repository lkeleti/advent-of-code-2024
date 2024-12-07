import java.util.ArrayList;
import java.util.List;

public class Equation {
    private Long testValue;
    private final List<Integer> numbers = new ArrayList<>();
    private boolean valid = false;

    public Equation(Long testValue) {
        this.testValue = testValue;
    }

    public Long getTestValue() {
        return testValue;
    }

    public void setTestValue(Long testValue) {
        this.testValue = testValue;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void addNumber(int number) {
        numbers.add(number);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Equation copy() {
        Equation e = new Equation(testValue);
        e.setValid(isValid());
        for (int n: getNumbers()) {
            e.addNumber(n);
        }
        return e;
    }
}
