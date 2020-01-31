import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lambda {
    public static void main(String[] args) throws IOException {
        String carrot = ">";
        String name = "";
        ArrayList<Expressions> applicationArray =null;
        while (!name.equals("exit")) {
            // Enter data using BufferReader
            System.out.print(carrot);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine
            name = reader.readLine();
            if (!name.equals("")) {
                char[] namearray = name.toCharArray();
                int commentsplit = namearray.length;
                for (int i = namearray.length - 1; i >= 0; i--) {
                    if (namearray[i] == (';')) {
                        commentsplit = i;
                    }
                }
                boolean charfound = false;
                int charpos = commentsplit;
                if (namearray[commentsplit - 1] == ' ') { // for removing spaces before the comment a____;comment
                                                          // removes the _____(<-spaces)
                    for (int i = commentsplit - 1; i >= 0; i--) {
                        if (charfound == false && namearray[i] == ' ') {
                            charpos = i;
                        }
                        if (namearray[i] != ' ') {
                            charfound = true;
                        }
                    }
                }
                char[] temp = new char[charpos];
                System.arraycopy(namearray, 0, temp, 0, charpos);

                namearray = temp;
                // System.out.println(Arrays.toString(temp));
                StringBuilder sbf = new StringBuilder("");
                sbf.append(namearray);
                name = sbf.toString();

                int numberofvariables = 0;
                if (temp[0] != ' ') {
                numberofvariables++;
                }
                for (int i = 0; i < temp.length - 1; i++) {
                if (temp[i] == ' ' && temp[i + 1] != ' ') {
                numberofvariables++;
                }
                }
                // ArrayList<Applications> appArray = new ArrayList<Applications>();
                // int letterindex = 0;
                // while(letterindex !=-1){
                //     letterindex = -1;

                //     Applications app = new Applications();
                //     for (int i = 0; i < temp.length; i++) {
                //         if(temp[i]!= '(' && temp[i] != ')' && letterindex == -1){
                //             letterindex = i;
                //             Variables var = new Variables(Character.toString(temp[i])); //change here if vars are larger than one 
                //             app.setLeft(var);
                //             appArray.add(app);
                //         }
                //     }
                //     if(letterindex != -1){
                //         int parenCounter = 1;
                //         int parenIndex = letterindex;
                //         int i = letterindex;
                //         while(i < temp.length && parenCounter != 0){
                //             if(temp[i] == '(' ){
                //                 parenCounter ++;
                //             }
                //             if(temp[i] == ')'){
                //                 parenCounter--;
                //             }
                //             if(parenCounter ==0){
                //                 parenIndex = i;
                //             }
                //             i++;
                //         }
                //         ArrayList<Object> rightArray = new ArrayList<>();
                //         for (int j = letterindex + 1; j < parenIndex; j++) {
                //             rightArray.add(temp[j]);
                //         }
                //         appArray.get(appArray.size()-1).right =rightArray; 

                //     }
                // }
            }
            //Variables var = new Variables(name);

            if (!name.equals("exit")) {
                //System.out.println(var.toString());
                // Expressions expression = new Expressions(var.toString());
                // System.out.println(expression.toString());
            } else {
                System.out.println("Goodbye!");
            }

        }
    }
}