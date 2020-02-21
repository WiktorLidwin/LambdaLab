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
        ArrayList<Expressions> applicationArray = null;
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
                    chararray.add(namearray[i]);
                }
                chararray.add(' ');
                if (!name.equals("exit")) {
                    System.out.println(createExpressions(chararray, 0));
                } else {
                    System.out.println("Goodbye!");
                }
                currApplication = new Applications();
            }
        }
    }

    public static ArrayList<Character> temparray = new ArrayList<>();
    public static ArrayList<Expressions> expressionarray = new ArrayList<>();
    public static Applications currApplication = new Applications();

    // public static Applications temp = new Applications();
    public static Applications createExpressions(List<Character> chararray, int index) {
        System.out.println("app:"+currApplication);
        if (index >= chararray.size()) {
            return currApplication;
        } else {
            if (chararray.get(index) == '(') {
                Applications temp = new Applications();
                temp.right = currApplication.right;
                temp.left = currApplication.left;
                currApplication.right = null;
                currApplication.left = null;
                createExpressions(chararray.subList(index + 1, getClosedParenIndex(chararray, index)), 0);

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
                System.out.println("app:" + currApplication);
                return createExpressions(chararray, getClosedParenIndex(chararray, index) + 1);
            } else if (chararray.get(index) == '\\') {

            } else if (chararray.get(index) == ')') {

            } else if (chararray.get(index) != ' ') {
                int varindex = getFullVarName(chararray, index);
                if (index == varindex) {
                    addToTree(listToString(chararray.subList(index, varindex + 1)));
                } else {
                    addToTree(listToString(chararray.subList(index, varindex)));
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
        return 0;
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
                    || chararray.get(i) == ')' || chararray.get(i) == ' ')) {

            } else {
                return i;
            }
        }
        return chararray.size() - 1;
    }
}
