import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Service {
    public String diskMap;
    public StringBuilder disk = new StringBuilder();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                diskMap = line;
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path, ioe);
        }
    }
    public long partOne() {
        createDisk();
        defragDisk();
        System.out.println(disk);
        return calculateCrc();
        //91088873473 low
    }

    private long calculateCrc() {
        long total = 0;
        for (int i = 0; i < disk.length(); i++) {
            char defChar = disk.charAt(i);
            if (defChar != '.') {
                total += i * (defChar - '0');
            }
        }
        return total;
    }

    private void defragDisk() {
        int diskPointer = disk.length()-1;
        while (diskPointer > 0) {
            char defId = disk.charAt(diskPointer);
            int freeSpace = disk.indexOf(".");
            if (freeSpace != -1 && freeSpace < diskPointer) {
                disk.setCharAt(freeSpace, defId);
                disk.setCharAt(diskPointer, '.');
            }
            diskPointer--;
        }
    }

    private void createDisk() {
        long id = 0;
        for (int pos = 0; pos < diskMap.length(); pos++) {
            int c = diskMap.charAt(pos) - '0';
            for (int i = 0; i < c; i++) {
                if (pos % 2 == 0) {
                    disk.append(id);
                } else {
                    disk.append(".");
                }
            }
            if (pos % 2 == 0) {
                id++;
            }
        }
    }

    public int partTwo() {
        return 0;
    }
}
