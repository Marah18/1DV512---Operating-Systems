public class Block {
    protected int startAdress;
    protected int endAdress;

    // this class for the free blocks without id
    public Block(int startAdress, int endAdress) {
        this.startAdress = startAdress;
        this.endAdress = endAdress;
    }

    public int getStartAdd() {
        return startAdress;
    }

    public void setStartAdd(int s) {
        startAdress = s;
    }

    public int getEndAdd() {
        return endAdress;
    }

    public void setEndAdd(int s) {
        endAdress = s;
    }

    public int calcBlockSize() {
        return getEndAdd() - getStartAdd() + 1;
    }
}
