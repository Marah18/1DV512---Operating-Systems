import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Reader {
    // to return the size of the memory from the first line of the input file
    public static int ReadSize(Scanner scanner) {
        int size = 0;
        while (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            if (text.trim().length() == 0)
                continue;
            size = Integer.parseInt(text);
            break;
        }
        return size;
    }

    // to write in the output file needed information of the memory
    public static String toWriteString(Memory m) {
        StringBuffer toWrite = new StringBuffer();
        toWrite.append("Allocated blocks \n");
        for (AllocatedBlock b : m.getAllocketedBlocks()) {
            toWrite.append(b.getId() + ";" + b.getStartAdd() + ";" + b.getEndAdd() + "\n");
        }
        toWrite.append("Free blocks \n");
        for (Block b : m.getBlockList()) {
            toWrite.append(b.getStartAdd() + ";" + b.getEndAdd() + "\n");
        }
        toWrite.append("Fregmentation \n");
        toWrite.append(m.fregmentation());
        toWrite.append("\nErrors\n");
        String s = toWrite.toString();
        return s;

    }

    // method to create file of current memory with its information and status
    public static void writer(String firstFit, String error, String bestFit, String error2, String worstFit,
            String error3, int counter) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("scenario" + counter + ".out" + counter, StandardCharsets.UTF_8, false));
            writer.write("First Fit\n");
            writer.write(firstFit);
            if (error.isEmpty()) {
                writer.write("NONE\n");
            } else {
                writer.write(error);
            }
            writer.write("\nBest Fit\n");
            writer.write(bestFit);
            if (error2.isEmpty()) {
                writer.write("NONE\n");
            } else {
                writer.write(error2);
            }
            writer.write("\nWorst Fit\n");
            writer.write(worstFit);
            if (error3.isEmpty()) {
                writer.write("NONE\n");
            } else {
                writer.write(error3);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method to create last file of current memory with its information and status
    public static void writer(String firstFit, String error, String bestFit, String error2, String worstFit,
            String error3) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("scenario.out", StandardCharsets.UTF_8, false));
            writer.write("First Fit" + "\n");
            writer.write(firstFit);
            if (error.isEmpty()) {
                writer.write("NONE\n");
            } else {
                writer.write(error);
            }
            writer.write("\nBest Fit\n");
            writer.write(bestFit);
            if (error2.isEmpty()) {
                writer.write("NONE\n");
            } else {
                writer.write(error2);
            }
            writer.write("\nWorst Fit\n");
            writer.write(worstFit);
            if (error3.isEmpty()) {
                writer.write("NONE\n");
            } else {
                writer.write(error3);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile() throws IOException {
        // buffers to add errors to it for the memory
        StringBuffer errorHistoryFirst = new StringBuffer();
        StringBuffer errorHistoryBest = new StringBuffer();
        StringBuffer errorHistoryWorst = new StringBuffer();

        String[] aStrings;
        // scan/read the input file
        try (Scanner scan = new Scanner(new File("senario.In"), Charset.defaultCharset())) {
            var text = "";
            int size = ReadSize(scan);
            // create three memories with the three different strategies
            FirstFitMemory memoryFirst = new FirstFitMemory(size);
            BestFitMemory memoryBest = new BestFitMemory(size);
            WorstFitMemory memoryWorst = new WorstFitMemory(size);
            // to print the number of the line/command
            int counter = 0;
            // counter for the number of the file that increases with 1 each time we create
            // a new file after "O" command
            int sinario = 1;

            // reading from the file line by line
            while (scan.hasNextLine()) {
                counter++;
                text = scan.nextLine();
                if (text.trim().length() == 0)
                    continue;
                // split the text with ";"
                aStrings = text.split(";");

                // Adding command to the list depending on command kind
                if (aStrings[0].equals("A")) {
                    int id = Integer.parseInt(aStrings[1]);
                    int length = Integer.parseInt(aStrings[2]);
                    // allocate the block in the three different memories
                    try {
                        memoryFirst.Allocate(id, length);
                    } catch (OutOfMemoryError error) {
                        errorHistoryFirst.append("A;" + counter + ";" + (memoryFirst.getLargestFreeBlockSize() + "\n"));
                    }
                    try {
                        memoryBest.Allocate(id, length);
                    } catch (OutOfMemoryError error) {
                        errorHistoryBest.append("A;" + counter + ";" + (memoryBest.getLargestFreeBlockSize() + "\n"));
                    }
                    try {
                        memoryWorst.Allocate(id, length);
                    } catch (OutOfMemoryError error) {
                        errorHistoryWorst.append("A;" + counter + ";" + (memoryWorst.getLargestFreeBlockSize() + "\n"));
                    }

                    // deallocate the block in the three different memories
                } else if (aStrings[0].equals("D")) {
                    int id = Integer.parseInt(aStrings[1]);
                    try {
                        memoryFirst.Deallocate(id);
                    } catch (OutOfMemoryError e) {
                        errorHistoryFirst.append("D;" + counter + e.getMessage() + "\n");
                    }
                    try {
                        memoryBest.Deallocate(id);
                    } catch (OutOfMemoryError e) {
                        errorHistoryBest.append("D;" + counter + e.getMessage() + "\n");
                    }
                    try {
                        memoryWorst.Deallocate(id);
                    } catch (OutOfMemoryError e) {
                        errorHistoryWorst.append("D;" + counter + e.getMessage() + "\n");
                    }
                    // compact memories
                } else if (aStrings[0].equals("C")) {
                    memoryFirst.compact();
                    ;
                    memoryBest.compact();
                    memoryWorst.compact();
                } else if (aStrings[0].equals("O")) {
                    // create file with the memory status and information
                    writer(toWriteString(memoryFirst), errorHistoryFirst.toString(), toWriteString(memoryBest),
                            errorHistoryBest.toString(),
                            toWriteString(memoryWorst), errorHistoryWorst.toString(), sinario);
                    sinario++;
                }

            }
            // create the last file with the memory status and information
            writer(toWriteString(memoryFirst), errorHistoryFirst.toString(), toWriteString(memoryBest),
                    errorHistoryBest.toString(),
                    toWriteString(memoryWorst), errorHistoryWorst.toString());
        }
    }
}
