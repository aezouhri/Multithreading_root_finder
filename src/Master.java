import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Constructor for the Master class
 * @author aezouhri
 */
public class Master {

    /**
     * Main method runs the different elements of our program
     * @param args Java command
     * @throws InterruptedException When a thread has been interrupted during its work
     */
    public static void main(String[] args) throws InterruptedException {

        //Creates a buffer for the roots to be stored in.
        CircularBufferM root_buffer = new CircularBufferM();
        //Creates a buffer for the coeficients to be stored in.
        CircularBufferM coeffcient_buffer = new CircularBufferM();

        ExecutorService service = Executors.newCachedThreadPool();

        //Creating the 10 slaves.
        Slave slave1 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave2 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave3 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave4 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave5 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave6 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave7 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave8 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave9 = new Slave(root_buffer, coeffcient_buffer);
        Slave slave10 = new Slave(root_buffer, coeffcient_buffer);


        service.execute(slave1);
        service.execute(slave2);
        service.execute(slave3);
        service.execute(slave4);
        service.execute(slave5);
        service.execute(slave6);
        service.execute(slave7);
        service.execute(slave8);
        service.execute(slave9);
        service.execute(slave10);

        Random r = new Random();

        Double[] random_coeff = new Double[]{};

        ArrayList<String[]> roots = new ArrayList<>();


        System.out.println("Choose if you want to generate:\n"+"1- 30 sets of coefficients.\n"+"2- 3000 sets of coefficients.\n"+"Choice:");
        Scanner input= new Scanner(System.in);
        int choice= input.nextInt();

        if(choice==1)
        {

            for (int i = 1; i <= 30; i++) {

            //Generates random numbers.
            double a = r.nextDouble() * 100; //because we keep getting small numbers
            double b = r.nextDouble() * 100; //because we keep getting small numbers
            double c = r.nextDouble() * 100; //because we keep getting small numbers

            random_coeff = new Double[]{a, b, c};

            //Puts the coefficients in their buffer.
            coeffcient_buffer.blockingPut(random_coeff);

            //Gets the roots from their buffer.
            Object[] answers = root_buffer.blockingGet();

            roots.add((String[]) answers);

        }
            for (int i = 0; i < 30; i++) {
                System.out.println(Arrays.toString(random_coeff) + " Coefficient set " + i);
                System.out.println("Answer set : \n" + Arrays.toString(roots.get(i)) + "\n");
            }
        }
        else if(choice==2)
        {
            for (int i = 1; i <= 3000; i++) {

                //Generates random numbers.
                double a = r.nextDouble() * 100; //because we keep getting small numbers
                double b = r.nextDouble() * 100; //because we keep getting small numbers
                double c = r.nextDouble() * 100; //because we keep getting small numbers

                random_coeff = new Double[]{a, b, c};

                //Puts the coefficients in their buffer.
                coeffcient_buffer.blockingPut(random_coeff);

                //Gets the roots from their buffer.
                Object[] answers = root_buffer.blockingGet();

                roots.add((String[]) answers);

            }

            System.out.println("Number of sets of coefficients solved by slaved 1 "+slave1.getWorked()+ "\nTime taken: "+ slave1.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 2 "+slave2.getWorked()+ "\nTime taken: "+ slave2.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 3 "+slave3.getWorked()+ "\nTime taken: "+ slave3.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 4 "+slave4.getWorked()+ "\nTime taken: "+ slave4.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 5 "+slave5.getWorked()+ "\nTime taken: "+ slave5.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 6 "+slave6.getWorked()+ "\nTime taken: "+ slave6.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 7 "+slave7.getWorked()+ "\nTime taken: "+ slave7.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 8 "+slave8.getWorked()+ "\nTime taken: "+ slave8.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 9 "+slave9.getWorked()+ "\nTime taken: "+ slave9.getTime_taken()+" ms.\n");
            System.out.println("Number of sets of coefficients solved by slaved 10 "+slave10.getWorked()+ "\nTime taken: "+ slave10.getTime_taken()+" ms.\n");

        }

        root_buffer.setFinished(true);
        coeffcient_buffer.setFinished(true);

        service.shutdown();
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
