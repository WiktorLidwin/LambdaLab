import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Lambda {
    public static void main(String[] args) throws IOException {
        String carrot = ">";
        String name = "";
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

                // int numberofvariables = 0;
                // if (temp[0] != ' ') {
                // numberofvariables++;
                // }
                // for (int i = 0; i < temp.length - 1; i++) {
                // if (temp[i] == ' ' && temp[i + 1] != ' ') {
                // numberofvariables++;
                // }
                // }
                if (namearray[0] == '(') {
                    // split the fucntion thing in lambda
                    int parenCounter = 0;
                    int lastParenIndex = 0;
                    while (true) {
                        for (int i = 0; i < namearray.length; i++) {
                            if (namearray[i] == '(') {
                                parenCounter++;
                                lastParenIndex =i;
                            }
                            
                            if (namearray[i] == ')') {
                                int spaceIndex = 0;
                                for (int j = lastParenIndex+1; j < i; j++) {
                                    if(namearray[j] == ' '){
                                        spaceIndex = j;
                                    }
                                }
                                char[] leftArray = new char[spaceIndex-1];
                                char[] rightArray = new char[namearray.length - spaceIndex-1];
                                System.arraycopy(namearray, 1, leftArray, 0, spaceIndex-1);
                                System.arraycopy(namearray, spaceIndex, rightArray, 0, namearray.length - spaceIndex-1);
                                Expressions expression = new Expressions(" ");
                                expression.setLeft(leftArray.toString());
                                expression.setRight(rightArray.toString());
                                parenCounter--;
                            }
                            
                        }
                    }

                    // String[] variableNameArray = name.split(" ");
                    // ArrayList<Variables> varaiableArray = new ArrayList<Variables>();
                    // for (int i = 0; i < variableNameArray.length; i++) {
                    //     varaiableArray.add(new Variables(variableNameArray[i]));
                    // }
                }
            }
            Variables var = new Variables(name);

            if (!name.equals("exit")) {
                System.out.println(var.toString());
                Expressions expression = new Expressions(var.toString());
                System.out.println(expression.toString());
            } else {
                System.out.println("Goodbye!");
            }

        }
    }
}