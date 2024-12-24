import java.util.Objects;

public class Formula {
    private String operation;
    private String input1;
    private String input2;
    private String output;

    public Formula(String operation, String input1, String input2, String output) {
        this.operation = operation;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getInput1() {
        return input1;
    }

    public void setInput1(String input1) {
        this.input1 = input1;
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Formula formula)) return false;
        return Objects.equals(getOperation(), formula.getOperation()) && Objects.equals(getInput1(), formula.getInput1()) && Objects.equals(getInput2(), formula.getInput2()) && Objects.equals(getOutput(), formula.getOutput());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getInput1(), getInput2(), getOutput());
    }
}
