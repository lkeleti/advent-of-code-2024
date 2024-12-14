import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final List<Robot> robots = new ArrayList<>();
    private final int maxX = 101;
    private final int maxY = 103;
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(" ");
                datas[0] = datas[0].replace("p=","");
                datas[1] = datas[1].replace("v=","");
                String[] position = datas[0].split(",");
                String[] velocity = datas[1].split(",");
                robots.add(new Robot(
                        new Cord(Integer.parseInt(position[0]),Integer.parseInt(position[1])),
                        new Cord(Integer.parseInt(velocity[0]),Integer.parseInt(velocity[1]))
                ));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        for (int sec = 0; sec < 100; sec++) {
            for (Robot robot: robots) {
                robot.move(maxX,maxY);
            }
        }
        return countRobots();
    }

    private int countRobots() {
        int halfX = maxX / 2;
        int halfY = maxY / 2;
        List<Integer> quadrant = new ArrayList<>(4);
        quadrant.add(0);
        quadrant.add(0);
        quadrant.add(0);
        quadrant.add(0);

        for (Robot robot: robots) {
            if (robot.getPosition().getPosX() < halfX && robot.getPosition().getPosY() < halfY) {
                quadrant.set(0,quadrant.get(0) + 1);
            }

            if (robot.getPosition().getPosX() > halfX && robot.getPosition().getPosY() < halfY) {
                quadrant.set(1,quadrant.get(1) + 1);
            }

            if (robot.getPosition().getPosX() > halfX && robot.getPosition().getPosY() > halfY) {
                quadrant.set(2,quadrant.get(2) + 1);
            }

            if (robot.getPosition().getPosX() < halfX && robot.getPosition().getPosY() > halfY) {
                quadrant.set(3,quadrant.get(3) + 1);
            }

        }
        return quadrant.stream()
                .reduce(1, (a, b) -> a * b);
    }
    public int partTwo() {
        return 0;
    }
}
