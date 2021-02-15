import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



    public class find_route{
    public static void main(String [] args){
        int rows;

        //*****************
        //***IMPORT FILE***
        //*****************
        File file = new File(args[0]);
        Scanner scan;
        try{
            scan = new Scanner(file); //get a row count
        }
        catch (FileNotFoundException e){
            System.out.println("File not found, exiting...");
            return;
        }
        rows = 0;
        while(scan.hasNextLine()){
            scan.nextLine();
            rows++;
        }
        rows--;// last line doesnt count
        try{
            scan = new Scanner(file); //reset scanner to read in data
        }
        catch (FileNotFoundException e){
            System.out.println("File not found, exiting...");
            return;
        }
        String [][] data = new String[rows][3]; //data will be stored here
        String str;
        String[] strArr;
        int i = 0;
        while(i < rows){
            str = scan.nextLine();
            strArr = str.split(" ", 0);
            data[i][0] = strArr[0]; //first coloumn is city A
            data[i][1] = strArr[1]; //second coloumn is city B
            data[i][2] = strArr[2]; //third coloumn is cost
            i++;
        }
        //*********************
        //***START ALGORITHM***
        //*********************
        int generated =0;
        int expanded = 0;
        String root = args[1]; //the beginning state
        String goal = args[2]; //the goal state
        String target = args[1]; // the one being expanded
        ArrayList<Node> frindge = new ArrayList<Node>(); //self explainitory
        ArrayList<String> closed = new ArrayList<String>();
        int index = 0; //index for frindge
        double temp = 500; //to compare which item in frindge has lowest g(n)
        if(args.length == 3)
        {
            //***********************
            //***UNINFORMED SEARCH***
            //***********************
            System.out.println("Uninformed Search Selected\n");
            Node node = new Node(); //initialize first state w/ 0 and null parent
            node.state = args[1];
            node.gofn=0.0;
            node.d=0;
            frindge.add(node);
            generated++; //arg[1] is start state and is technically generated

            while(target != goal) {
                if(frindge.isEmpty()){
                    System.out.println("Fringe Empty. Goal Not Found, Generating Output");
                    printFail(generated,expanded);
                    return;
                }
                boolean pass = false; //bool for passing a closed state
                temp = 9999;//just so search would find lowest val
                for (i = 0; i < frindge.size(); i++) //loop to find lowest g(n) value in frindge
                {
                    if (frindge.get(i).gofn < temp) {
                        temp = frindge.get(i).gofn;
                        index = i;
                        target = frindge.get(i).state;
                    }
                }
                for (String S : closed) {//search thru closed and see if matches expanding node
                    if (S.equals(target)) {
                        expanded++;
                        frindge.remove(index);
                        pass = true;

                    }
                }
                if (pass) { //continue and not do anything since closed = target
                    continue;
                }
                expanded++; //done expanding one node, increment counter
                if (target.equals(goal)) {
                    break;
                }
                for (i = 0; i < rows; i++) //find edges
                {
                    if (data[i][0].equals(target)) { //if A matches names with target
                        node = new Node();
                        node.state = data[i][1];
                        node.gofn = Double.parseDouble(data[i][2]) + frindge.get(index).gofn;
                        node.d = frindge.get(index).d + 1;
                        node.parent = frindge.get(index);
                        frindge.add(node);
                        generated++;
//                        Node.printme(node);
                    }
                    if (data[i][1].equals(target)) { //if B matches names with target
                        node = new Node();
                        node.state = data[i][0];
                        node.gofn = Double.parseDouble(data[i][2]) + frindge.get(index).gofn;
                        node.d = frindge.get(index).d + 1;
                        node.parent = frindge.get(index);
                        frindge.add(node);
                        generated++;
//                        Node.printme(node);
                    }
                }
                closed.add(frindge.get(index).state);
                frindge.remove(index);
            }
//            System.out.println("Generated: "+generated);
//            System.out.println("Expanded: "+expanded);
            printPass(frindge.get(index),generated,expanded);

//            System.out.println("Generated: "+ generated);
//            System.out.println("Expanded: "+expanded);
//            System.out.println("Frindge: ");
//            for (Node x : frindge) {
//                Node.printme(x);

//            }
//            System.out.print("Closed: [");
//            for (String S : closed) {
//                System.out.println(S + " ");
//            }
//            System.out.println(" ]");
        }
        else {
            //*********************
            //***INFORMED SEARCH***
            //*********************
            System.out.println("Informed Search Selected\n");
            file = new File(args[3]);
            try{
                scan = new Scanner(file); //get a row count
            }
            catch (FileNotFoundException e){
                System.out.println("File not found, exiting...");
                return;
            }
            int rows2 = 0;
            while(scan.hasNextLine()){
                scan.nextLine();
                rows2++;
            }
            rows2--;// last line doesnt count
            try{
                scan = new Scanner(file); //reset scanner to read in data
            }
            catch (FileNotFoundException e){
                System.out.println("File not found, exiting...");
                return;
            }
            String [][] data2 = new String[rows][2]; //data will be stored here
            i = 0;
            while(i < rows2){
                str = scan.nextLine();
                strArr = str.split(" ", 0);
                data2[i][0] = strArr[0]; //first coloumn is city
                data2[i][1] = strArr[1]; //second coloumn is distance f(n)
                i++;
            }
            /**
             * Done importing second file
             */
            ArrayList<NodeInformed> frindge2 = new ArrayList<NodeInformed>(); //self explainitory
            NodeInformed node = new NodeInformed(); //initialize first state w/ 0 and null parent
            node.state = args[1];
            node.gofn=0.0;
            for(int j=0;j < rows2;j++) //go to second input sheet and fine f(n) val
            {
                if(data2[j][0].equals(target)) //if second sheet name equals target
                {
                    node.fofn= Double.parseDouble(data2[j][1]) + node.gofn;
                }
            }
            node.d=0;
            frindge2.add(node);
            generated++; //arg[1] is start state and is technically generated
//            for(i = 0;i<28;i++){
//                for (int j = 0;j<2;j++)
//                {
//                    System.out.print(data2[i][j]+" ");
//                }
//                System.out.println("");
//            }

            while(target != goal) {
                if(frindge2.isEmpty()){
                    System.out.println("Fringe Empty. Goal Not Found, Generating Output");
                    printFail(generated,expanded);
                    return;
                }
                boolean pass = false; //bool for passing a closed state
                temp = 9999;//just so search would find lowest val
                for (i = 0; i < frindge2.size(); i++) //loop to find lowest g(n) value in frindge
                {
                    if (frindge2.get(i).fofn < temp) {
                        temp = frindge2.get(i).fofn;
                        index = i;
                        target = frindge2.get(i).state;
                    }
                }
                for (String S : closed) {//search thru closed and see if matches expanding node
                    if (S.equals(target)) {
                        expanded++;
                        frindge2.remove(index);
                        pass = true;

                    }
                }
                if (pass) { //continue and not do anything since closed = target
                    continue;
                }
                expanded++; //done expanding one node, increment counter
                if (target.equals(goal)) {
                    break;
                }
                for (i = 0; i < rows; i++) //find edges
                {
                    if (data[i][0].equals(target)) { //if A matches names with target
                        node = new NodeInformed();
                        node.state = data[i][1];
                        node.gofn = Double.parseDouble(data[i][2]) + frindge2.get(index).gofn;
                        for(int j=0;j < rows2;j++) //go to second input sheet and fine f(n) val
                        {
                            if(node.state.equals(data2[j][0])) //if second sheet name equals target
                            {
                                node.fofn= Double.parseDouble(data2[j][1])+ Double.parseDouble(data[i][2]);
                            }
                        }

                        //algorithm to fine fn val here
                        node.d = frindge2.get(index).d + 1;
                        node.parent = frindge2.get(index);
                        frindge2.add(node);
                        generated++;
//                        Node.printme(node);
                    }
                    if (data[i][1].equals(target)) { //if B matches names with target
                        node = new NodeInformed();
                        node.state = data[i][0];
                        node.gofn = Double.parseDouble(data[i][2]) + frindge2.get(index).gofn;
                        for(int j =0;j < rows2;j++) //go to second input sheet and fine f(n) val
                        {
                            if(node.state.equals(data2[j][0])) //if second sheet name equals target
                            {
                                node.fofn= Double.parseDouble(data2[j][1])+ Double.parseDouble(data[i][2]);
                            }
                        }
                        //algorithm to fine fn val here
                        node.d = frindge2.get(index).d + 1;
                        node.parent = frindge2.get(index);
                        frindge2.add(node);
                        generated++;
//                        Node.printme(node);
                    }
                } //end of for loop to find edges
                closed.add(frindge2.get(index).state);
                frindge2.remove(index);
            }   //end of while loop
            printPass(frindge2.get(index),generated,expanded);
        }


    } //end of main
        public static void printFail(int gen, int exp){
            System.out.println("Nodes expanded: "+exp);
            System.out.println("Nodes generated: "+gen);
            System.out.println("Distance: infinity");
            System.out.println("route:\nnone");
        }//end of printFail
        public static void printPass(Node head, int gen, int exp)
        {
            int total = head.d+1;
            int index = 0;
            double temp = 0.0;
            String[] state = new String[total];
            double[] km = new double[total];
            Node CurrNode = head;
            while(CurrNode != null)
            {
                state[index] = CurrNode.state;
                km[index] = CurrNode.gofn;
                index++;
                CurrNode = CurrNode.parent;
            }
            index--;
            System.out.println("Nodes expanded: "+exp);
            System.out.println("Nodes generated: "+gen);
            System.out.println("Distance: "+head.gofn);
            System.out.println("route:");
            for (int i = index;i>0;i--)
            {
                if(i != 1)
                {
                    System.out.println(state[i]+" to "+state[i-1]+", "+km[i-1]+" km");
                }
                else
                {
                    System.out.println(state[i]+" to "+state[i-1]+", "+(km[i-1]-temp)+" km");
                }
                temp = temp + km[i-1];
            }

        } //end of printPass
        public static void printPass(NodeInformed head, int gen, int exp) //function overloading for different node types
        {
            int total = head.d+1;
            int index = 0;
            double temp = 0.0;
            String[] state = new String[total];
            double[] km = new double[total];
            NodeInformed CurrNode = head;
            while(CurrNode != null)
            {
                state[index] = CurrNode.state;
                km[index] = CurrNode.gofn;
                index++;
                CurrNode = CurrNode.parent;
            }
            index--;
            System.out.println("Nodes expanded: "+exp);
            System.out.println("Nodes generated: "+gen);
            System.out.println("Distance: "+head.gofn);
            System.out.println("route:");
            for (int i = index;i>0;i--)
            {
                if(i != 1)
                {
                    System.out.println(state[i]+" to "+state[i-1]+", "+km[i-1]+" km");
                }
                else
                {
                    System.out.println(state[i]+" to "+state[i-1]+", "+(km[i-1]-temp)+" km");
                }
                temp = temp + km[i-1];
            }
        } //end of printPass
}

    public class Node {
        String state;
        double gofn;
        int d;
        Node parent;
        public static void printme(Node head){
            Node CurrNode = head;
            while(CurrNode != null)
            {
                System.out.print("< state = " +CurrNode.state+" g(n) = "+CurrNode.gofn+", d = "+CurrNode.d+", Parent = Pointer to {");
                CurrNode = CurrNode.parent;
            }
            System.out.println("}");
        }
    }
public class NodeInformed {
    String state;
    double gofn;
    double fofn;
    int d;
    NodeInformed parent;
    public static void printme(NodeInformed head){
        NodeInformed CurrNode = head;
        while(CurrNode != null)
        {
            System.out.print("< state = " +CurrNode.state+" g(n) = "+CurrNode.gofn+", d = "+CurrNode.d+", f(n) = "+CurrNode.fofn+", Parent = Pointer to {");
            CurrNode = CurrNode.parent;
        }
        System.out.println("}");
    }
}

