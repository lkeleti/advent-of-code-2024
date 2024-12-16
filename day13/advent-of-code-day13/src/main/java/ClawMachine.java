public class ClawMachine {
    private long ax,ay, bx, by,px,py;

    public ClawMachine(long ax, long ay, long bx, long by, long px, long py) {
        this.ax = ax;
        this.ay = ay;
        this.bx = bx;
        this.by = by;
        this.px = px;
        this.py = py;
    }

    public long getAx() {
        return ax;
    }

    public void setAx(long ax) {
        this.ax = ax;
    }

    public long getBx() {
        return bx;
    }

    public void setBx(long bx) {
        this.bx = bx;
    }

    public long getAy() {
        return ay;
    }

    public void setAy(long ay) {
        this.ay = ay;
    }

    public long getBy() {
        return by;
    }

    public void setBy(long by) {
        this.by = by;
    }

    public long getPx() {
        return px;
    }

    public void setPx(long px) {
        this.px = px;
    }

    public long getPy() {
        return py;
    }

    public void setPy(long py) {
        this.py = py;
    }
}
