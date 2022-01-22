/**
 * CircularBufferM class, gets and sets the roots and coefficients.
 */
public class CircularBufferM{

    /**
     * Buffer shared between master and slave.
     */
    private final Object[][] buffer = {{},{},{}}; // shared buffer

    /**
     *Counts the number of buffers used.
     */
    private int occupiedCells = 0; // count number of buffers used

    /**
     * Index of the next element to write.
     */
    private int writeIndex = 0; // index of next element to write to

    /**
     * Index of the next element to read.
     */
    private int readIndex = 0; // index of next element to read


    /**
     * Variable used to see if the buffer is done with its assignment
     */
    private boolean finished= false;

    /**
     * Gets if the buffer is done with its assignment.
     * @return true or false.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     *Sets if the buffer is done with it's assignment.
     * @param finished true or false.
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Puts the given object no matter what type it is in the buffer.
     * @param object_to_put given object
     * @throws InterruptedException
     */
    // place value into buffer
    public synchronized void blockingPut(Object[] object_to_put) throws InterruptedException {
        // wait until buffer has space available, then write value;
        // while no empty locations, place thread in waiting state


        while (occupiedCells == buffer.length && !isFinished())
        {
//            System.out.println("Buffer is full. Producer waits.%n "+ name);
            wait(); // wait until a buffer cell is free

        }

        buffer[writeIndex] = object_to_put; // set new buffer value

        // update circular write index
        writeIndex = (writeIndex + 1) % buffer.length;

        occupiedCells++; // one more buffer cell is full

        notifyAll(); // notify threads waiting to read from buffer

    }

    /**
     * Gets the objects present in the buffer.
     * @return object stored in the buffer.
     * @throws InterruptedException
     */
    // return value from buffer
    public synchronized Object [] blockingGet() throws InterruptedException {
        // wait until buffer has data, then read value;
        // while no data to read, place thread in waiting state
        while (occupiedCells == 0 && !isFinished()) {
//                System.out.println("Buffer is empty. Consumer waits. " + name);
            wait(); // wait until a buffer cell is filled
        }

        Object [] readValue = buffer[readIndex]; // read value from buffer

        // update circular read index
        readIndex = (readIndex + 1) % buffer.length;

        occupiedCells--; // one fewer buffer cells are occupied

        notifyAll(); // notify threads waiting to write to buffer

        return readValue;
    }


}


/**************************************************************************
 * (C) Copyright 1992-2015 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/