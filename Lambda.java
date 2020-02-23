import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Lambda {

    public static void main(String[] args) throws IOException {
        String carrot = ">";
        String name = "";
        ArrayList<Expressions> applicationArray = null;
        while (!name.equals("exit")) {
            // Enter data using BufferReader
            System.out.print(carrot);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));

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
                if (namearray[commentsplit - 1] == ' ') { // for removing spaces before the comment a____;comment //
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
                StringBuilder sbf = new StringBuilder("");
                sbf.append(namearray);
                name = sbf.toString();
                ArrayList<Character> chararray = new ArrayList<>();
                for (int i = 0; i < namearray.length; i++) {
                    if (namearray[i] == '(')
                        chararray.add(' ');
                    if (namearray[i] == '=')
                        chararray.add(' ');

                    if ((int) namearray[i] == 0)
                        chararray.add('\\');
                    else
                        chararray.add(namearray[i]);
                }
                chararray.add(' ');
                if (!name.equals("exit")) {
                    Expressions express = createExpressions(chararray, 0);
                    if (express != null)
                        System.out.println(express);
                } else {
                    System.out.println("Goodbye!");
                }
                currApplication = new Applications();
            }
        }
    }

    public static Dictionary VariableMap = new Hashtable();
    public static ArrayList<Character> temparray = new ArrayList<>();
    public static ArrayList<Expressions> expressionarray = new ArrayList<>();
    public static Applications currApplication = new Applications();
    public static Function currFunction = new Function();
    public static Object key = null;

    public static Expressions createExpressions(List<Character> chararray, int index) {
        if (index >= chararray.size()) {
            if (currApplication.right == null && currApplication.left instanceof String) {
                // System.out.println("is a streing");
                Variables temp = new Variables((String) currApplication.left);
                return temp;
            } else if (currApplication.right == null) {
                // System.out.println("is a function");
                return (Expressions) currApplication.left;
            }
            return currApplication;
        } else {
            if (chararray.get(index) == '(') {
                Applications temp = new Applications();
                temp.right = currApplication.right;
                temp.left = currApplication.left;
                currApplication.right = null;
                currApplication.left = null;
                Expressions express = createExpressions(
                        chararray.subList(index + 1, getClosedParenIndex(chararray, index)), 0);
                if (express instanceof Function) {
                    Applications temp2 = new Applications();
                    if (temp.left == null && temp.right == null) {
                        temp2.left = express;
                    } else if (temp.left == null) {
                        temp2.left = temp.right;
                        temp2.right = express;
                    } else if (temp.right == null) {
                        temp2.left = temp.left;
                        temp2.right = express;
                    } else {
                        temp2.right = express;
                        temp2.left = temp;
                    }
                    currApplication = temp2;
                } else {
                    Applications temp2 = new Applications();
                    if (temp.left == null && temp.right == null) {
                        temp2 = currApplication;
                    } else if (temp.left == null) {
                        temp2.left = temp.right;
                        temp2.right = checkRightExpress();
                    } else if (temp.right == null) {
                        temp2.left = temp.left;
                        temp2.right = checkRightExpress();
                    } else {
                        temp2.right = checkRightExpress();
                        temp2.left = temp;
                    }
                    currApplication = temp2;
                }
                // System.out.println("app:" + currApplication);
                return createExpressions(chararray, getClosedParenIndex(chararray, index) + 1);
            } else if (chararray.get(index) == '\\') {
                while (chararray.get(index + 1) == ' ') {
                    index++;
                }
                int varindex = getFullVarName(chararray, index + 1);
                Function temp = new Function();
                currFunction = temp;
                if (index == varindex) {
                    System.out.println("same in and varin:" + Character.toString(chararray.get(varindex)) + ".");
                    temp.setVar(Character.toString(chararray.get(varindex)));
                } else {
                    String varname = "";
                    for (int i = index + 1; i < varindex; i++) {
                        varname += chararray.get(i);
                    }
                    Variables temp2 = new Variables(varname);
                    temp.setVar(temp2);
                }

                while (chararray.get(varindex) != '.') {
                    varindex++;
                }

                Applications temp2 = new Applications();
                temp2.right = currApplication.right;
                temp2.left = currApplication.left;
                currApplication.right = null;
                currApplication.left = null;
                Expressions express = new Expressions() {
                };
                express = createExpressions(chararray.subList(varindex + 1, chararray.size()), 0);
                if (express instanceof Applications) {
                    Applications z = new Applications();
                    z.left = ((Applications) express).left;
                    z.right = ((Applications) express).right;
                    express = z;
                }
                temp.setExpression(express);
                if (!(temp2.left == null && temp2.right == null)) {
                    currApplication.left = temp2.left;
                    currApplication.right = temp2.right;
                    addToTree(temp);
                    return currApplication;
                }
                return temp;
            } else if (chararray.get(index) == ')') {
                System.out.println("error");
            } else if (chararray.get(index) == '=') {
                Object var = new Object();
                var = key;
                if (VariableMap.get(var) == null) {
                    currApplication = new Applications();
                    VariableMap.put(var, createExpressions(chararray, index + 1));
                    System.out.println(("Added " + VariableMap.get(var) + " as " + var));
                } else {
                    System.out.println(var + " is already defined.");
                }
                return null;
            } else if (chararray.get(index) != ' ') {
                int varindex = getFullVarName(chararray, index);
                if (index == varindex) {
                    String name = listToString(chararray.subList(index, varindex + 1));
                    if (name.equals("run")) {
                    } else {
                        key = name;
                        if (VariableMap.get(name) != null) {
                            addToTree(VariableMap.get(name));
                        } else
                            addToTree(name);
                    }
                } else {
                    String name = listToString(chararray.subList(index, varindex));
                  
                    if (name.equals("run")) {
                    } else {  
                        key = name;
                        if (VariableMap.get(name) != null) {
                            addToTree(VariableMap.get(name));
                        } else
                            addToTree(name);
                    }
                }
                return createExpressions(chararray, varindex + 1);
            } else {
                return createExpressions(chararray, index + 1);
            }
        }
        return null;
    }

    public static Object checkRightExpress() {
        if (currApplication.left == null && currApplication.right == null) {
            return null;
        } else if (currApplication.left == null) {
            return currApplication.right;
        } else if (currApplication.right == null) {
            return currApplication.left;
        } else {
            return currApplication;
        }
    }

    public static String listToString(List<Character> chararray) {
        String temp = "";
        for (int i = 0; i < chararray.size(); i++) {
            temp += chararray.get(i);
        }
        return temp;
    }

    public static void addToTree(Object var) {
        // System.out.println("added");
        if (currApplication.left == null) {
            currApplication.left = var;
        } else if (currApplication.right == null) {
            currApplication.right = var;
        } else {
            Applications temp = new Applications();
            temp.left = currApplication;
            temp.right = var;
            currApplication = temp;
        }
    }

    public static int getClosedParenIndex(List<Character> chararray, int currindex) {
        int parenCount = 1;
        for (int i = currindex + 1; i < chararray.size(); i++) {
            if (chararray.get(i) == '(') {
                parenCount++;
            }
            if (chararray.get(i) == ')') {
                parenCount--;
            }
            if (parenCount == 0) {
                return i;
            }
        }
        return chararray.size() - 1;
    }

    public static String getDotIndex(List<Character> chararray, int currindex) {
        String temp = "";
        for (int i = currindex + 1; i < chararray.size(); i++) {
            if (!(chararray.get(i) == '.')) {
                return temp;
            } else {
                temp += Character.toString(chararray.get(i));
            }
        }
        return temp;
    }

    public static int getFullVarName(List<Character> chararray, int currindex) {

        for (int i = currindex; i < chararray.size(); i++) {
            if (!(chararray.get(i) == '(' || chararray.get(i) == '\\' || chararray.get(i) == '.'
                    || chararray.get(i) == ')' || chararray.get(i) == ' ' || chararray.get(i) == '=')) {

            } else {
                // System.out.println("geti: "+ chararray.get(i-1));
                return i;
            }
        }
        return chararray.size();
    }
}