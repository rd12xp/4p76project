/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofm;

import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author randy
 */
public class SOFM {
    public String[][] testSamples;  
    public String[][] testSamplesiris;  
            
    public SOFM(){
        
        double[][] colours = {
           {0, 0, 0},
           {0, 0, 1},
           {0, 0, 0.5},
           {0.125, 0.529, 1.0},
           {0.33, 0.4, 0.67},
           {0.6, 0.5, 1.0},
           {0, 1, 0},
           {1, 0, 0},
           {0, 1, 1},
           {1, 0, 1},
           {1, 1, 0},
           {1, 1, 1},
           {.33, .33, .33},
           {.5, .5, .5},
           {.66, .66, .66}
            };
        int count=1;
        Scanner reader = new Scanner(System.in);  // to get input from user
        int seed = 19947779;
        String binaryNum;
        
        
        double[] input = new double[9];
        double[] inputIris = new double[4];
        
        NeuralNetwork parityNetwork = new NeuralNetwork(1603, 3, 1600);

        Random random = new Random(seed);
        
        for (int a = 0; a < 400; a++) { 
        //shuffle them
        int index;
        double[] temp;
        for (int c = 0; c < 5; c++) {
            for (int i = 14; i > 0; i--) {
                index = random.nextInt(i + 1);
                temp = colours[index];
                colours[index] = colours[i];
                colours[i] = temp;
            }
        }

        String expected;
        double correct = 0;
        //for (int a = 0; a < 1000; a++) { // the epoch for the training set
            for (int n = 0; n < 15; n++) {//the 600 out of 682 used for training
                for (int i = 0; i < 3; i++) {
                    input[i] = colours[n][i];//get input
                }
                if (parityNetwork.trainingExample(input, 0.1, 6000, count)) {
                    correct++;
                } // entering training data
                count++;
            }
            System.out.println("--------------------------------");
            System.out.println(" PERCENTAGE CORRECT: " + (correct / 681));
            System.out.println("--------------NEXT--------------");
            correct = 0;
            
        //}
        }

        // testing
        double[][] coloursTest = {
           {0, 0, 0},
           {0, 0, 1},
           {0, 0, 0.5},
           {0.125, 0.529, 1.0},
           {0.33, 0.4, 0.67},
           {0.6, 0.5, 1.0},
           {0, 1, 0},
           {1, 0, 0},
           {0, 1, 1},
           {1, 0, 1},
           {1, 1, 0},
           {1, 1, 1},
           {.33, .33, .33},
           {.5, .5, .5},
           {.66, .66, .66}
            };
        for (int n = 0; n < 15; n++) {//the 600 out of 682 used for training
            for (int i = 0; i < 3; i++) {
                input[i] = coloursTest[n][i];//get input
            }
            //parityNetwork.trainingExample(input, 0.1, 390, count);
            System.out.println("here");
        }
        JFrame frame = new JFrame("Display");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(630, 640);
        
        JPanel display = new JPanel(new GridLayout(40,40));
        JPanel insideSquare;
        Color c1;

        for(int i=0; i<1600; i++){
            c1 = new Color((int)(parityNetwork.networkWeights[0][i]*255),(int)(parityNetwork.networkWeights[1][i]*255),(int)(parityNetwork.networkWeights[2][i]*255));
            insideSquare = new JPanel();
            insideSquare.setBackground(c1);
            display.add(insideSquare);
            frame.add(insideSquare);
            frame.setVisible(true);
        }
        

    }
    
    // colours correspond too 
    /*
    ['black', 'blue', 'darkblue', 'skyblue',
         'greyblue', 'lilac', 'green', 'red',
         'cyan', 'violet', 'yellow', 'white',
         'darkgrey', 'mediumgrey', 'lightgrey']
    */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SOFM();
    };
    
}