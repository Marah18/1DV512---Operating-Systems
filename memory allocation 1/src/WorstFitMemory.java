import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WorstFitMemory extends Memory {

    public WorstFitMemory(int size) {
        super(size);
    }

    public void Allocate(int id, int size) {
        if (size > this.Size) {
            TriedToAllocateBlock.add(id);
            throw new OutOfMemoryError();
        }
        MemorySortWorstFit(freeBlocks);
        for (int i = 0; i < freeBlocks.size(); i++) {
            var block = freeBlocks.get(i);
            if (block.endAdress - block.startAdress > size - 1) {
                var newALoocatedBlock = new AllocatedBlock(block.startAdress, block.startAdress + size - 1, id);
                block.startAdress = block.startAdress + size;
                AllocatedBlocks.add(newALoocatedBlock);
                SortBlockList(freeBlocks);
                return;
            } else if (block.endAdress - block.startAdress == size - 1) {
                var newALoocatedBlock = new AllocatedBlock(block.startAdress, block.endAdress, id);
                freeBlocks.remove(block);
                AllocatedBlocks.add(newALoocatedBlock);
                SortBlockList(AllocatedBlocks);
                SortBlockList(freeBlocks);

                return;
            }
        }
        SortBlockList(freeBlocks);
        TriedToAllocateBlock.add(id);
        throw new OutOfMemoryError();

    }

    // sorting array of blocks from the biggest size to the smallest sequentially
    public <B extends Block> void MemorySortWorstFit(ArrayList<B> list) {
        Collections.sort(list, new Comparator<B>() {
            public int compare(B b1, B b2) {
                if (b1.startAdress == b2.startAdress)
                    return 0;

                return (b1.endAdress - b1.startAdress) > (b2.endAdress - b2.startAdress) ? -1 : 1;
            }
        });
    }
}
