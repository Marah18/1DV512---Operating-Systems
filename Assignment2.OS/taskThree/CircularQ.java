import java.util.concurrent.Semaphore;

// class to circular queue implements the interface IMessageQueue
public class CircularQ implements IMessageQueue {
    // queue size
    private int size;
    // index of the first element in the queue
    private int front;
    // index of the last element in the queue
    private int rear;
    // list of messages
    private char[] messages;
    // semaphore that control recieving messages
    private Semaphore recieveMessageSemaphore;
    // semaphore that control sending messages
    private Semaphore sendMessageSemaphore;
    // semaphore to protect shared data from being preempted
    private Semaphore accessQueue;

    public CircularQ(int size) {
        this.size = size;
        this.front = -1;
        this.rear = -1;
        messages = new char[size];
        sendMessageSemaphore = new Semaphore(size);
        recieveMessageSemaphore = new Semaphore(0);
        accessQueue = new Semaphore(1);
    }

    @Override
    public boolean Send(char message) {
        try {
            // if we can send message and the queue is not blocked
            sendMessageSemaphore.acquire();
            accessQueue.acquire();
        } catch (InterruptedException e) {
            System.out.println("The queue is full !");
            return false;
        }
        // check if the queue is empty
        if (front == -1) {
            front++;
        }
        // change the index of the rear
        rear = (rear + 1) % size;
        // add the message/ char to the rear of the queue
        messages[rear] = message;
        recieveMessageSemaphore.release();
        accessQueue.release();
        return true;
    }

    private char removeMessage() {
        // The message/elemeny we should remove is the first element in the queue >>
        // first in first out
        char firsMessage = messages[front];
        // if the queue consists only from one message
        if (front == rear) {
            front = rear = -1;
        } else {
            front = (front + 1) % size;
        }
        return firsMessage;
    }

    @Override
    public char Recv() {
        try {
            // if we can recieve message and the queue is not blocked
            recieveMessageSemaphore.acquire();
            accessQueue.acquire();
        } catch (InterruptedException e1) {
            System.out.println("Can't recieve");
        }

        // remove the message of the front of the queue and and return it to the
        // reciever thread to be printed in the screen
        var message = removeMessage();
        sendMessageSemaphore.release();
        accessQueue.release();
        return message;
    }

}
