/**
 * Slave class, it computes the root of a given set of coefficients.
 * @author aezouhri
 */
public class Slave implements Runnable{

    /**
     * Roots of a set of coefficients.
     */
    private double first_root,second_root;

    /**
     * Buffer containing the roots.
     */
    private CircularBufferM root_buffer;

    /**
     * Buffer containing the coefficient sets.
     */
    private CircularBufferM coeff_buffer;

    /**
     * Number of slaves being run.
     */
    private int number_of_slaves=0;

    /**
     * Slave number
     */
    private int slave_number;

    /**
     * How many roots did a slave find.
     */
    private int worked=0;

    /**
     * Time taken by a slave to find the roots.
     */
    private long time_taken=0;

    /**
     * Gets the time taken by a slave to finish all it's assignments
     * @return the time taken.
     */
    public long getTime_taken() {
        return time_taken;
    }

    /**
     * Sets the time it took a slave to finish all it's assignments
     * @param time_taken time it took to finish
     */
    public void setTime_taken(long time_taken) {
        this.time_taken = time_taken;
    }

    /**
     * Number of roots the slave found.
     * @return number of roots.
     */
    public int getWorked() {
        return worked;
    }

    /**
     * Sets the number of the slave
     * @param slave_number number of the slave.
     */
    public void setSlave_number(int slave_number) {
        this.slave_number = slave_number;
    }



    /**
     * Slave Constructor
     * @param root_buffer Buffer of containing the roots.
     * @param coeff_buffer Buffer containing the coefficient.
     */
    Slave(CircularBufferM root_buffer, CircularBufferM coeff_buffer)
    {
        this.root_buffer = root_buffer;
        this.coeff_buffer = coeff_buffer;
        setSlave_number(number_of_slaves);
        number_of_slaves++;
    }

    /**
     * This method computes the root for the given generated coefficients from the coefficient buffer.
     * And puts the roots in their buffer.
     * @param A x^2 coefficient
     * @param B x coefficient
     * @param C constant
     * @return A String[]
     */
    public String[] computeRoot(double A, double B, double C)
    {

        double delta= (B*B)-4*A*C;
        String [] answer = new String[2];

        if(A==0)
        {
            System.out.println("Not a quadratic equation");

        }
        else if(delta >0)
        {
            first_root= (-B+Math.sqrt(delta))/(2*A);
            second_root= (-B-Math.sqrt(delta))/(2*A);
            answer[0]= "Root one "+ first_root;
            answer[1]= "Root two "+ second_root;

        }
        else if(delta == 0)
        {
            first_root= (-B)/(2*A);
            second_root=first_root;
            answer[0]= "Root one "+ first_root;
            answer[1]= "Root two "+ second_root;
        }
        else if(delta < 0)
        {

            first_root= -B/(2*A);
            second_root= (Math.sqrt(-delta))/(2*A);
            answer[0]= String.format("Root one: %2f + %2fi",first_root,second_root);
            answer[1]= String.format("Root two: %2f - %2fi",first_root,second_root);

        }

        return answer;
    }

    /**
     * This methods puts the computed root in their buffer.
     */
    @Override
    public void run() {
        String[] roots;

        while (!root_buffer.isFinished()) {
            try {
                Thread.sleep(10);
                Double[] coeff = (Double[]) coeff_buffer.blockingGet();
                worked++;

                long start = System.currentTimeMillis();

                roots = computeRoot(coeff[0], coeff[1], coeff[2]);

                long end = System.currentTimeMillis();

//                setNumber_of_answer(getNumber_of_answer() + 1);

                root_buffer.blockingPut(roots);
                setTime_taken(getTime_taken() +(end-start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
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

