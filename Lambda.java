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
        ArrayList<Expressions> ExpressionsArray =null;
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
                   ExpressionsArray = new ArrayList<Expressions>();
                    ArrayList<Object> inputArray = new ArrayList<Object>();
                    for (int i = 0; i < namearray.length; i++) {
                        inputArray.add(namearray[i]);
                    }
                    int lastParenIndex = 0;
                    while (inputArray.size() != 1) {
                        for (int i = 0; i < inputArray.size(); i++) {
                            if (inputArray.get(i).equals('(')) {
                                lastParenIndex = i;
                            }

                            if (inputArray.get(i).equals(')')) {
                                int spaceIndex = 0;
                                for (int j = lastParenIndex + 1; j < i; j++) {
                                    if (inputArray.get(j).equals(' ')) {
                                        spaceIndex = j;
                                    }
                                }
                                // ArrayList<Object> leftArray =
                                // inputArray.subList(lastParenIndex+1,spaceIndex);
                                // ArrayList<Object> rightArray = inputArray.subList(spaceIndex+1,i);
                                ArrayList<Object> leftArray = new ArrayList<>();
                                ArrayList<Object> rightArray = new ArrayList<>();
                                for (int j = lastParenIndex + 1; j < spaceIndex; j++) {
                                    leftArray.add(inputArray.get(j));
                                }
                                for (int j = spaceIndex + 1; j < i; j++) {
                                    rightArray.add(inputArray.get(j));
                                }

                                // char[] leftArray = new char[spaceIndex-1];
                                // char[] rightArray = new char[namearray.length - spaceIndex-1];
                                // System.arraycopy(namearray, 1, leftArray, 0, spaceIndex-1);
                                // System.arraycopy(namearray, spaceIndex, rightArray, 0, namearray.length -
                                // spaceIndex-1);
                                Expressions expression = new Expressions(" ");
                                expression.setLeft(leftArray);
                                expression.setRight(rightArray);
                                ExpressionsArray.add(expression);

                                System.out.println(leftArray);
                                System.out.println(rightArray);
                                System.out.println(i);
                                for (int j = lastParenIndex; j < i + 1; j++) {
                                    inputArray.remove(lastParenIndex);
                                }
                                inputArray.add(lastParenIndex, expression);
                                System.out.println(inputArray);
                            }

                        }
                    }

                    // String[] variableNameArray = name.split(" ");
                    // ArrayList<Variables> varaiableArray = new ArrayList<Variables>();
                    // for (int i = 0; i < variableNameArray.length; i++) {
                    // varaiableArray.add(new Variables(variableNameArray[i]));
                    // }
                }
            }
            Variables var = new Variables(name);

            if (!name.equals("exit")) {
                System.out.println(var.toString());
                // Expressions expression = new Expressions(var.toString());
                // System.out.println(expression.toString());
                if(!ExpressionsArray.equals(null)){
                    System.out.println("rer");
                    System.out.println(ExpressionsArray.get(ExpressionsArray.size()-1));
                }
            } else {
                System.out.println("Goodbye!");
            }

        }
    }
}