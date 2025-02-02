import java.util.ArrayList;
import java.util.List;

public class HolderForQueue {
    private int[] intDatas;
    private List<Integer[]> path = new ArrayList<>();

    public HolderForQueue(int[] intDatas, List<Integer[]> path) {
        this.intDatas = intDatas;
        this.path = path;
    }

    public int[] getIntDatas() {
        return intDatas;
    }

    public List<Integer[]> getPath() {
        return path;
    }

    public void setIntDatas(int[] intDatas) {
        this.intDatas = intDatas;
    }

    public void setPath(List<Integer[]> path) {
        this.path = path;
    }

    public void addToPath(Integer[] p) {
        path.add(p);
    }

    public List<Integer[]> copyPath() {
        List<Integer[]> newPath = new ArrayList<>();
        for (Integer[] p: path) {
            newPath.add(new Integer[]{p[0], p[1]});
        }
        return newPath;
    }
}
