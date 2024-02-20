import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public abstract class Memory {
    protected ArrayList<Block> freeBlocks;
    protected ArrayList<AllocatedBlock> AllocatedBlocks;
    protected ArrayList<Integer> TriedToAllocateBlock;
    protected int Size;

    public Memory(int size) {
        this.freeBlocks = new ArrayList<Block>();
        this.AllocatedBlocks = new ArrayList<AllocatedBlock>();
        this.Size = size;
        this.freeBlocks.add(new Block(0, size - 1));
        this.TriedToAllocateBlock = new ArrayList<Integer>();
    }

    // return the sum size of free blocks
    public int getFreeBlocksSize() {
        int sum = 0;
        for (Block b : freeBlocks) {
            sum += b.calcBlockSize();
        }
        return sum;
    }

    // return the largest free block in the memory
    public Block getLargestFreeBlock() {
        Block largest = freeBlocks.get(0);
        for (Block b : freeBlocks) {
            if (b.calcBlockSize() > largest.calcBlockSize()) {
                largest = b;
            }
        }
        return largest;
    }

    // return the size of the largest free block
    public int getLargestFreeBlockSize() {
        return ((getLargestFreeBlock().getEndAdd() - getLargestFreeBlock().getStartAdd()) + 1);
    }

    public void Deallocate(int id) {
        // loop intp allocaed blocks to check if the id of the block exist
        for (int i = 0; i < AllocatedBlocks.size(); i++) {
            var block = AllocatedBlocks.get(i);
            // if we find the id of the block attempted to deallocate
            if (block.getId() == id) {
                // create filltered arraylist of the free neighbor blocks of the the block
                // attempted to
                // deallocate, the neighbor blocks adds if thier end address is next to the
                // start address of the block attempted to allocate or versa versa
                var freeNeighborBlocks = new ArrayList<Block>(freeBlocks.stream()
                        .filter(b -> block.getEndAdd() + 1 == b.getStartAdd()
                                || block.getStartAdd() - 1 == b.getEndAdd())
                        .collect(Collectors.toList()));

                // remove free Neighbor Blocks from the free block array
                freeBlocks.removeAll(freeNeighborBlocks);
                // remove block attempted to allocate from allocated blocks array
                AllocatedBlocks.remove(block);
                // add the deallocated block to free Neighbor Blocks array
                freeNeighborBlocks.add(block);
                SortBlockList(freeNeighborBlocks);
                // creating the new free block after deallocating
                var newFreeBlock = new Block(freeNeighborBlocks.get(0).getStartAdd(),
                        freeNeighborBlocks.get(freeNeighborBlocks.size() - 1).getEndAdd());
                // add the new free block to free blocks array
                freeBlocks.add(newFreeBlock);
                SortBlockList(freeBlocks);
                return;
            }
        }
        // check if the block we wanted to deallocate was never attempted to be
        // allocated before
        for (int i : TriedToAllocateBlock) {
            if (i == id) {
                throw new OutOfMemoryError(";1");
            }
        }
        // the reason for failure should be set to 0 if the ID to deallocate was never
        // attempted to be allocated
        throw new OutOfMemoryError(";0");

    }

    // to compact the memory i move the start address to first allocated block of
    // the array to 0 and make no free blocks between allocated blocks
    // delete all free blocks from free blocks array and add one free block that
    // start from the end of the last allocated block
    public void compact() {
        SortBlockList(AllocatedBlocks);
        int n = -1;
        for (int i = 0; i < AllocatedBlocks.size(); i++) {
            n = i;
            var block = AllocatedBlocks.get(i);
            if (i == 0) {
                int size = block.endAdress - block.startAdress;
                block.startAdress = 0;
                block.endAdress = block.startAdress + size;
            } else {
                int size = block.endAdress - block.startAdress;
                block.startAdress = AllocatedBlocks.get(i - 1).endAdress + 1;
                block.endAdress = block.startAdress + size;
            }
        }
        if (n != -1) {
            freeBlocks.clear();
            Block b = new Block(AllocatedBlocks.get(n).endAdress + 1, Size - 1);
            freeBlocks.add(b);
        }
    }

    public Iterable<Integer> getTriedToAllocateBlock() {
        return TriedToAllocateBlock;
    }

    public ArrayList<AllocatedBlock> getAllocketedBlocks() {
        SortBlockListById(AllocatedBlocks);
        return AllocatedBlocks;
    }

    public ArrayList<Block> getBlockList() {
        return freeBlocks;
    }

    public int getMemorySize() {
        return Size;
    }

    public void removeBlock(Block block) {
        freeBlocks.remove(block);
    }

    public void addBlock(Block block) {
        freeBlocks.add(block);
    }

    // sort an array of blocks by starting address, so the lowest starting address
    // to the highest starting address.
    protected <B extends Block> void SortBlockList(ArrayList<B> list) {
        Collections.sort(list, new Comparator<B>() {
            public int compare(B b1, B b2) {
                if (b1.startAdress == b2.startAdress)
                    return 0;
                return b1.startAdress < b2.startAdress ? -1 : 1;
            }
        });
    }

    // sort an array of blocks by id, so the lowest id first
    protected <B extends AllocatedBlock> void SortBlockListById(ArrayList<AllocatedBlock> list) {
        Collections.sort(list, new Comparator<AllocatedBlock>() {
            public int compare(AllocatedBlock b1, AllocatedBlock b2) {
                if (b1.id == b2.id)
                    return 0;
                return b1.id < b2.id ? -1 : 1;
            }
        });
    }

    // method to calulate the fregemntation
    public double fregmentation() {
        double f = ((double) getLargestFreeBlock().calcBlockSize()) / getFreeBlocksSize();
        // to round the double to only 6 decimal digits
        f = Math.round(f * 1000000.0) / 1000000.0;
        return (1 - f);
    }
}
