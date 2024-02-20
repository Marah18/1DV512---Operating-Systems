import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BestFitMemory extends Memory {

    public BestFitMemory(int size) {
        super(size);
    }

    public void Allocate(int id, int size) {
        // check if the size of the memory is larger thean the size of the block wanted
        // to allocate
        if (size > this.Size) {
            TriedToAllocateBlock.add(id);
            throw new OutOfMemoryError();
        }
        // sort free blocks
        MemorySortBestFit(freeBlocks);
        // loop into free blocks to find a free place for the new block
        for (int i = 0; i < freeBlocks.size(); i++) {
            var block = freeBlocks.get(i);
            // check if the blocks size we want to allocate fits in the free blocks by their
            // start and end addresses
            if (block.endAdress - block.startAdress > size - 1) {
                // the start address of new allocated block will be the same as the start
                // address of the free block
                // the end address will be the start address of the free block plus the size of
                // the allocated block - 1
                var newALoocatedBlock = new AllocatedBlock(block.startAdress, block.startAdress + size - 1, id);
                // add the size of the allocated block to the start address of the free block
                block.startAdress = block.startAdress + size;
                // add the new allocated block to the allocated blocks array
                AllocatedBlocks.add(newALoocatedBlock);
                SortBlockList(AllocatedBlocks);
                // end the method here
                return;

                // this condition if the size of free block is equl to the blocks size we want
                // to allocate
                // in this case the free block removes form the free blocks array
            } else if (block.endAdress - block.startAdress == size - 1) {
                var newALoocatedBlock = new AllocatedBlock(block.startAdress, block.endAdress, id);
                freeBlocks.remove(block);
                AllocatedBlocks.add(newALoocatedBlock);
                SortBlockList(AllocatedBlocks);
                return;
            }
        }
        // add the id of the block we couldn't allocate for some reason to the array of
        // the blocks we couldn't allocate
        TriedToAllocateBlock.add(id);
        throw new OutOfMemoryError();

    }

    // returning a sorted array sorted from the smallast block to the biggeset
    // sequentially
    public <B extends Block> void MemorySortBestFit(ArrayList<B> list) {
        Collections.sort(list, new Comparator<B>() {
            public int compare(B b1, B b2) {
                if (b1.startAdress == b2.startAdress)
                    return 0;

                return (b1.endAdress - b1.startAdress) < (b2.endAdress - b2.startAdress) ? -1 : 1;
            }
        });
    }

}
