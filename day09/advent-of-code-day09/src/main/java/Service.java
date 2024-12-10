import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    public String diskMap;
    public List<DiskBlock> blocks = new ArrayList<>();
    public int maxId = -1;
    public final Map<Integer, List<Integer>> fileDesc = new TreeMap<>();
    public final Map<Integer, Integer> emptyDesc = new TreeMap<>();

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
        return calculateCrc();
    }

    private long calculateCrc() {
        long total = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getBlockType() == BlockType.ID) {
                total += i * (blocks.get(i).getId());
            }
        }
        return total;
    }

    private void defragDisk() {
        int diskPointer = blocks.size() - 1;
        while (diskPointer > 0) {
            DiskBlock defDiskBlock = blocks.get(diskPointer);
            if (defDiskBlock.getBlockType() == BlockType.ID) {
                int freeSpace = -1;
                for (int i = 0; i < diskPointer; i++) {
                    if (blocks.get(i).getBlockType() == BlockType.EMPTY) {
                        freeSpace = i;
                        break;
                    }
                }
                if (freeSpace != -1) {
                    Collections.swap(blocks, freeSpace, diskPointer);
                }
            }
            diskPointer--;
        }
    }

    private void createDisk() {
        int id = 0;
        int position = 0;
        for (int pos = 0; pos < diskMap.length(); pos++) {
            int c = diskMap.charAt(pos) - '0';
            for (int i = 0; i < c; i++) {
                if (pos % 2 == 0) {
                    blocks.add(new DiskBlock(BlockType.ID, id));
                    maxId = id;
                    if (!fileDesc.containsKey(id)) {
                        fileDesc.put(id, new ArrayList<>());
                    }
                    fileDesc.get(id).add(position);
                } else {
                    blocks.add(new DiskBlock(BlockType.EMPTY));
                }
                position++;
            }
            if (pos % 2 == 0) {
                id++;
            }
        }
    }

    public int partTwo() {
        blocks.clear();
        fileDesc.clear();
        createDisk();
        findFreeSpace();
        return 0;
    }

    private void findFreeSpace() {
        boolean first = true;
        int start = -1;
        int size = 0;
        for (int i=0; i < blocks.size();i++) {
            if (blocks.get(i).getBlockType() == BlockType.EMPTY) {
                if (first) {
                    start = i;
                    first = false;
                }
                size++;
            } else {
                if (!first) {
                    emptyDesc.put(start,size);
                    first = true;
                    size = 0;
                }
            }
        }
    }
}
