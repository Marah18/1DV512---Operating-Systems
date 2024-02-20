import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FirstFitMemory extends Memory {

    public FirstFitMemory(int size) {
        super(size);
    }

    public void Allocate(int id, int size) {
        if (size > this.Size) {
            TriedToAllocateBlock.add(id);
            throw new OutOfMemoryError();
        }
        MemorySortFirstFit(AllocatedBlocks);
        for (int i = 0; i < freeBlocks.size(); i++) {
            var block = freeBlocks.get(i);
            if (block.endAdress - block.startAdress > size - 1) {
                var newAllocatedBlock = new AllocatedBlock(block.startAdress, block.startAdress + size - 1, id);
                block.startAdress = block.startAdress + size;
                AllocatedBlocks.add(newAllocatedBlock);
                SortBlockList(freeBlocks);
                return;
            } else if (block.endAdress - block.startAdress == size - 1) {
                var newALoocatedBlock = new AllocatedBlock(block.startAdress, block.endAdress, id);
                freeBlocks.remove(block);
                AllocatedBlocks.add(newALoocatedBlock);
                MemorySortFirstFit(AllocatedBlocks);
                return;
            }
        }
        TriedToAllocateBlock.add(id);
        throw new OutOfMemoryError();
    }

    // sort blocks by starting address, so the lowest starting address to the
    // highest starting address.
    public <B extends Block> void MemorySortFirstFit(ArrayList<B> list) {
        Collections.sort(list, new Comparator<B>() {
            public int compare(B b1, B b2) {
                if (b1.startAdress == b2.startAdress)
                    return 0;
                return b1.startAdress < b2.startAdress ? -1 : 1;
            }
        });
    }

}
