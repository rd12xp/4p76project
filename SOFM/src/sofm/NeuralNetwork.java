/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofm;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author randy
 */
public class NeuralNetwork {
    int seed = 9987;
    double[][] networkWeights;
    double[][] weightChange,oldweightChange;
    double[][] deltaLearn;
    double[] inputNodes;
    double active;
    double[][] outputNode;
    double[] hiddenNodes,backpropNodes,dist;
    double learningRate=0.5;
    int totalNodes; 
    int numInput;
    int numSum;
    int numOutput;
            
    public NeuralNetwork(int totalNodes1, int numInput1, int numOutput1){
        totalNodes=totalNodes1; 
        numInput=numInput1;
        //numSum=numHidden1+numOutput1;
        numOutput = numOutput1;
        Random random = new Random(seed);
        networkWeights = new double[numInput1][numOutput1];
        outputNode = new double[(int)Math.sqrt(numOutput1)][(int)Math.sqrt(numOutput1)];


        //nodes 0-3 are the input nodes
        //nodes 4-11 are the nodes in the single hidden layer
        //node 12 is the output node
        
        //setting up the random weights from the input to output latice
        for(int x=0; x<numInput; x++){
            for(int y=0; y<numOutput;y++){
                double ranWeight;
                boolean neg;
                ranWeight = (double)random.nextFloat();
                neg = random.nextBoolean();
                if(ranWeight==0){// dont let it be 0
                    ranWeight = (double)random.nextFloat();
                }
                if(neg){
                    ranWeight = -(ranWeight);
                }
                
                networkWeights[x][y] = ranWeight;

            }
        }
        
         
    }
    
    public boolean trainingExample(double[] input, double startLearn, double totalIteration, double numIteration){
        dist = new double[numOutput]; // distance between input and output nodes
        inputNodes = new double[numInput];
        //backpropNodes = new double[numHidden];
        boolean correct=false; 
        double error = 0;
        
        inputNodes = input;
        
        //calculating and finding winning node
        double min =10000;
        int winner=0;
        
        for(int i=0; i<numOutput; i++){
            dist[i] = sum(i);
            if(dist[i]<min){
                min = dist[i];
                winner = i;
            }
        }
        int winnerx = (int)(winner/100);
        int winnery =(winner-winnerx*100);
        System.out.println("the winning node is: " + winnerx +", " + winnery);
        //calculate neighbourhood---------------------------
        
        // the starting radius which is half the width of the lattice
        double radius = Math.sqrt(numOutput)/2;
        double lambda = totalIteration/Math.log(radius);
        //neigbourhood radius with decay
        double nRadius = radius* Math.exp((-(double)numIteration)/lambda);
        
        
        
        //----------------Adjusting neighbouring weights------------------
        
        // learning rate with decay
        double learningRate = startLearn * Math.exp((-(double)numIteration)/totalIteration);
        //how much the weight changes based on its distance to the winner
        double influence=0;
        
        for(int x=0; x< numOutput; x++){
            if(distW(x,winner)<nRadius){
                influence = Math.exp(-(Math.pow(distW(x,winner),2))/(2*Math.pow(nRadius,2)));
                for(int n =0; n<numInput; n++){
                    networkWeights[n][x]= networkWeights[n][x]+ influence*learningRate*(inputNodes[n]-networkWeights[n][x]);
                }
            }
        }

        
        return correct;
    }
    public boolean testingExample(double[] input, double learn, double momentum,double delta, String expected, double activation){
       
        return true;
        
    }
    
    private double sum(int i){ //distacnce from the input to the node calculation
        double sum=0;
        for(int y=0;y<numInput;y++){
            sum = sum + Math.pow((inputNodes[y]-networkWeights[y][i]),2);
            
        }
        return Math.sqrt(sum);
    }
    
    private double distW(int i, int win){
        double sum=0;
        int x, winX;
        int y, winY;
        
        //get location
        x = (int)(i/100);
        y = i - x*100;
        
        // get the winners location
        winX = (int)(win/100);
        winY = win - winX*100;
        
        return Math.sqrt(Math.pow((x-winX),2) + Math.pow((y-winY),2));
    }
    private double[] outputSum(){
        double[] sum = new double[3];
        for (int g =totalNodes-numOutput; g<totalNodes; g++){
            for(int y=numInput;y<totalNodes-numOutput;y++){
                sum[g-(totalNodes-numOutput)]=sum[g-(totalNodes-numOutput)] + (networkWeights[g][y])*hiddenNodes[y-numInput];
            }
        }
        return sum;
    }
    
}

// one-d to 2-d for lattice
// x is divide by 10 then round down
//y is subtract the new x