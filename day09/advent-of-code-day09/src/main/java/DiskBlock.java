public class DiskBlock {
    private BlockType blockType;
    private int id = -1;

    public DiskBlock(BlockType blockType, int id) {
        this.blockType = blockType;
        this.id = id;
    }

    public DiskBlock(BlockType blockType) {
        this.blockType = blockType;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if (blockType == BlockType.EMPTY) {
            return ".";
        } else {
            return "" + id;
        }
    }
}
