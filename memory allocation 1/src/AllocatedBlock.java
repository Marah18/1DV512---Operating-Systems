public class AllocatedBlock extends Block {
    public int id;

    // This class for blocks that would by successfully allocated in the memory
    public AllocatedBlock(int startAdress, int endAdress, int id) {
        super(startAdress, endAdress);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
