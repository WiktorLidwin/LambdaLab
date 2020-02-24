import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Lambda {

    public static void main(String[] args) throws IOException {
        String carrot = ">";
        String name = "";
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
                    run = false;
                    populate = false;
                    varCount = 0;
                    checkForVar = true;
                    Expressions express = createExpressions(chararray, 0);
                    Expressions past = null;
                    while (express != past) {
                        past = express;
                        express = runner(express);
                    }
                    if (populate) {
                        System.out.println("Populated numbers " + aInt + " to " + bInt);
                        Enumeration enm = VariableMap.keys();
                        List<Expressions> ll = Collections.list(enm);
                        System.out.println("size " + ll.size());
                        for (int i = 0; i < ll.size(); i++) {
                            System.out.println(i + ": " + ll.get(i));
                        }
                        enm = VariableMap.elements();
                        ll = Collections.list(enm);
                        System.out.println("size2 " + ll.size());
                        for (int i = 0; i < ll.size(); i++) {
                            System.out.println(i + "2: " + ll.get(i));
                        }
                    } else if (settingVar != null) {
                        VariableMap.put(settingVar, express);
                        System.out.println(("Added " + VariableMap.get(settingVar) + " as " + settingVar));
                        settingVar = null;
                    } else if (express != null) {
                        boolean foundVar = false;
                        if(checkForVar){
                        // System.out.println(express);//aExpressionArray
                        Enumeration enm = VariableMap.elements();
                        List<Expressions> ll = Collections.list(enm);
                        for (int i = 0; i < ll.size(); i++) {
                            sameExpression = true;
                            aExpressionArray = new ArrayList<>();
                            bExpressionArray = new ArrayList<>();
                            compareExpressions(express, ll.get(i));
                            if (sameExpression) {
                                System.out.println("found");
                                Enumeration enm2 = VariableMap.keys();
                                List<String> ll2 = Collections.list(enm2);
                                System.out.println(ll2.get(i));
                                foundVar = true;
                            }
                        }
                    }
                        if (!foundVar)
                            System.out.println(express);
                    
                    } else {
                        System.out.println("null");
                    }
                } else {
                    System.out.println("Goodbye!");
                }
                currApplication = new Applications();
            }
        }
    }

    public static Expressions runner(Expressions express) {// run \n.\f.\x.f (n f x) λf.λx.f x
        // System.out.println("ruN: "+run);
        if (express != null && run != true) {
            return express;
        } else if (run == true && (express instanceof Variables || express instanceof Function)) {
            return express;
        } else {
            int count = 0;
            Expressions temp2 = (Applications) express;
            while (temp2 != null && temp2 instanceof Applications) {
                count++;
                temp2 = (Expressions) ((Applications) temp2).left;
            }
            if (temp2 instanceof Function) {
                temp2 = (Applications) express;
                for (int i = 0; i < count - 1; i++) {
                    temp2 = (Expressions) ((Applications) temp2).left;
                }
                Expressions temp3 = runExpressions(temp2);
                count--;
                while (count != 0) {
                    temp2 = (Applications) express;
                    for (int i = 0; i < count - 1; i++) {
                        temp2 = (Expressions) ((Applications) temp2).left;
                    }
                    temp2 = (Expressions) ((Applications) temp2).right;
                    Applications app = new Applications();
                    app.left = temp3;
                    app.right = temp2;
                    temp3 = runExpressions(app);
                    count--;
                }
                return temp3;
            } else {
                return express;
            }

        }
    }

    // FAIL CASES:
    // ((λn.((n (λp.((p (λf.(λx.x))) (λx.(λy.x))))) (λx.(λy.x)))) (λf.(λx.(f (f (f
    // (f (f x))))))))

    // ((lambda n.((n (lambda p.((p (lambda f.(lambda x.x))) (lambda x.(lambda
    // y.x))))) (lambda x.(lambda y.x)))) (lambda f.(lambda x.(f (f (f (f (f
    // x))))))))
    public static Dictionary VariableMap = new Hashtable();
    public static Dictionary ReplaceVarMap = new Hashtable();
    public static ArrayList<Character> temparray = new ArrayList<>();
    public static ArrayList<Expressions> expressionarray = new ArrayList<>();
    public static Applications currApplication = new Applications();
    public static Function currFunction = new Function();
    public static Object key = null;
    public static boolean run = false;
    public static ArrayList<String> varArray = new ArrayList<>();
    public static String settingVar = null;
    static Expressions test = new Expressions() {
    };

    public static Expressions runExpressions(Expressions express) {
        express = alphaReduction(express);
        express = betaReduction(express);
        return express;
    }

    public static Expressions alphaReduction(Expressions express) {
        varArray = new ArrayList<>();
        ReplaceVarMap = new Hashtable();
        findVarNames((Expressions) ((Applications) express).right);
        replaceNames((Expressions) ((Applications) express).left);
        return express;
    }

    public static Expressions betaReduction(Expressions express) {
        Expressions temp2 = new Expressions() {
        };
        temp2 = express;
        if (express instanceof Function) {
            express = keepReplacingExpress(express, ((Expressions) ((Function) express).expression), express);
            if (temp2 != express) {
                // System.out.println("call");
                express = betaReduction(express);
            }
        } else if (express instanceof Applications) {
            // express = alphaReduction(express);
            if (((Applications) express).left instanceof Function) {
                Expressions sub = (Expressions) ((Applications) express).right;
                Variables var = ((Function) ((Applications) express).left).var;
                express = replaceExpressions((Expressions) ((Applications) express).left,
                        ((Function) (Expressions) ((Applications) express).left).expression, sub, var, express);
                express = betaReduction(express);
            } else {
                if (((Applications) express).left instanceof Variables) {
                } else {
                    Expressions temp = new Expressions() {
                    };
                    temp = betaReduction((Expressions) ((Applications) express).left);
                    express = keepReplacingExpress(express, temp, express);
                }
            }
        } else {
            System.out.println("bruh: " + express);
        }
        changed = true;
        while (changed == true) {
            changed = false;
            // System.out.println(express);
            Expressions temp = new Expressions() {
            };
            if (express instanceof Function) {
                temp = ((Function) express).expression;
                express = keepReplacingExpress(express, temp, express);
            } else if (express instanceof Applications) {
                temp = (Expressions) ((Applications) express).left;
                express = keepReplacingExpress(express, temp, express);
                temp = (Expressions) ((Applications) express).right;
                express = keepReplacingExpress(express, temp, express);
            }
        }
        return express;
    }

    // TEST - 2 1
    public static boolean changed = false;

    public static Expressions keepReplacingExpress(Expressions parent, Expressions root, Expressions express) {
        if (root instanceof Applications) {
            if (((Applications) root).left instanceof Function) {

                Expressions temp3 = replaceExpressions((Expressions) ((Applications) root).left,
                        ((Function) (Expressions) ((Applications) root).left).expression,
                        (Expressions) ((Applications) root).right, ((Function) ((Applications) root).left).var, root);
                if (parent instanceof Applications) {
                    if (((Applications) parent).left == root) {
                        ((Applications) parent).left = temp3;
                    } else if (((Applications) parent).right == root) {
                        ((Applications) parent).right = temp3;
                    }
                } else if (parent instanceof Function) {
                    ((Function) parent).expression = temp3;
                }
            } else {
                keepReplacingExpress(root, (Expressions) ((Applications) root).left, express);
                keepReplacingExpress(root, (Expressions) ((Applications) root).right, express);
            }
        } else if (root instanceof Function) {
            keepReplacingExpress(root, (Expressions) ((Function) root).expression, express);
        }
        return express;
    }

    public static Expressions replaceExpressions(Expressions parent, Expressions root, Expressions sub, Variables var,
            Expressions express) {
        if (root instanceof Applications) {
            replaceExpressions(root, (Expressions) ((Applications) root).left, sub, var, express);
            replaceExpressions(root, (Expressions) ((Applications) root).right, sub, var, express);
        } else if (root instanceof Variables) {
            if (((Variables) root).name.equals(var.name)) {
                changed = true;
                if (parent instanceof Applications && (Expressions) ((Applications) parent).left instanceof Variables
                        && ((Variables) (Expressions) ((Applications) parent).left).name.equals(var.name)) {
                    ((Applications) parent).left = deepCopy(sub);
                } else if (parent instanceof Applications
                        && (Expressions) ((Applications) parent).right instanceof Variables
                        && ((Variables) (Expressions) ((Applications) parent).right).name.equals(var.name)) {
                    ((Applications) parent).right = sub;
                } else if (parent instanceof Function) {
                    ((Function) parent).expression = sub;
                }
            }
        } else {
            if (!(((Function) root).var.name.equals(var.name)))
                replaceExpressions(root, (Expressions) ((Function) root).expression, sub, var, express);
        }
        return ((Function) (Expressions) ((Applications) express).left).expression;
    }

    public static void replaceNames(Expressions root) {
        if (root instanceof Applications) {
            replaceNames((Expressions) ((Applications) root).left);
            replaceNames((Expressions) ((Applications) root).right);
        } else if (root instanceof Variables) {
            if ((varArray.contains(((Variables) root).name))) {
                Enumeration enm = ReplaceVarMap.keys();
                List<String> ll = Collections.list(enm);
                if (ll.contains(((Variables) root).name)) {
                    ((Variables) root).name = (String) ReplaceVarMap.get(((Variables) root).name);
                } else {
                    // System.out.println("in1");
                    int count = 1;
                    String newName = (((Variables) root).name) + count;
                    while ((varArray.contains(newName))) {
                        count++;
                        newName = (((Variables) root).name) + count;
                    }
                    ReplaceVarMap.put(((Variables) root).name, newName);
                    ((Variables) root).name = newName;

                }
            }
        } else {
            if ((varArray.contains(((Function) root).var.name))) {
                Enumeration enm = ReplaceVarMap.keys();
                List<String> ll = Collections.list(enm);
                if (ll.contains(((Function) root).var.name)) {
                    ((Function) root).var.name = (String) ReplaceVarMap.get(((Function) root).var.name);
                } else {
                    int count = 1;
                    String newName = (((Function) root).var.name) + count;
                    while ((varArray.contains(newName))) {
                        count++;
                        newName = (((Function) root).var.name) + count;
                    }
                    ReplaceVarMap.put(((Function) root).var.name, newName);
                    ((Function) root).var.name = newName;
                }
            }
            replaceNames((Expressions) ((Function) root).expression);
        }
    }

    static void findVarNames(Expressions root) {
        if (root instanceof Applications) {
            findVarNames((Expressions) ((Applications) root).left);
            findVarNames((Expressions) ((Applications) root).right);
        } else if (root instanceof Variables) {
            if (!(varArray.contains(((Variables) root).name))) {
                varArray.add(((Variables) root).name);
            }
        } else {
            if (!(varArray.contains(((Function) root).var.name))) {
                varArray.add(((Function) root).var.name);
            }
            findVarNames((Expressions) ((Function) root).expression);
        }
    }
    public static boolean checkForVar = true;
    public static Expressions createExpressions(List<Character> chararray, int index) {
        if (index >= chararray.size()) {
            if (currApplication.right == null && currApplication.left instanceof String) {
                Variables temp = new Variables((String) currApplication.left);
                return temp;
            } else if (currApplication.right == null) {
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
                    // VariableMap.put(var, ;
                    settingVar = (String) var;
                    System.out.println("setting Var: " + settingVar);
                    return createExpressions(chararray, index + 1);
                } else {
                    System.out.println(var + " is already defined.");
                }
                return null;
            } else if (chararray.get(index) != ' ') {
                int varindex = getFullVarName(chararray, index);
                if (index == varindex) {
                    String name = listToString(chararray.subList(index, varindex + 1));
                    if (name.equals("run")) {
                        run = true;
                    } else if (name.equals("populate")) {
                        int z = getFullVarName(chararray, varindex + 1);
                        populate(listToString(chararray.subList(varindex, z)),
                                listToString(chararray.subList(z, chararray.size())));
                        populate = true;
                    } else {
                        key = name;
                        if (VariableMap.get(name) != null) {
                            if(varindex+1 >= chararray.size() && varCount ==0){
                                checkForVar = false;
                            }
                            varCount++;
                            addToTree(deepCopy((Expressions) VariableMap.get(name)));
                        } else {
                            Variables temp = new Variables(name);
                            addToTree(temp);
                        }
                    }
                } else {
                    String name = listToString(chararray.subList(index, varindex));

                    if (name.equals("run")) {
                        run = true;
                    } else if (name.equals("populate")) {
                        int z = getFullVarName(chararray, varindex + 1);
                        populate(listToString(chararray.subList(varindex, z)),
                                listToString(chararray.subList(z, chararray.size())));
                        populate = true;
                    } else {
                        key = name;
                        if (VariableMap.get(name) != null) {
                            if(varindex+1 >= chararray.size() && varCount ==0){
                                checkForVar = false;
                            }
                            varCount++;
                            addToTree(deepCopy((Expressions) VariableMap.get(name)));
                        } else {
                            Variables temp = new Variables(name);
                            varCount++;
                            addToTree(temp);
                        }
                    }
                }
                return createExpressions(chararray, varindex + 1);
            } else {
                return createExpressions(chararray, index + 1);
            }
        }
        return null;
    }
    public static int varCount = 0;
    public static boolean populate = false;
    public static int aInt = 0;
    public static int bInt = 0;

    public static void populate(String a, String b) {
        aInt = Integer.parseInt(a.trim());
        bInt = Integer.parseInt(b.trim());
        System.out.println("a" + aInt);
        System.out.println("b" + bInt);

        String succString = "succ = \\n.\\f.\\x.f (n f x)";
        char[] temp = succString.toCharArray();
        ArrayList<Character> chararray = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == '(')
                chararray.add(' ');
            if (temp[i] == '=')
                chararray.add(' ');

            if ((int) temp[i] == 0)
                chararray.add('\\');
            else
                chararray.add(temp[i]);
        }
        chararray.add(' ');
        System.out.println(chararray);
        Expressions express = createExpressions(chararray, 0);
        Expressions past = null;
        while (express != past) {
            past = express;
            express = runner(express);
        }
        VariableMap.put("succ", express);
        System.out.println(("Added " + VariableMap.get("succ") + " as " + "succ"));

        if (aInt == 0) {
            String zeroString = "0 = \\f.\\x.x";
            temp = zeroString.toCharArray();
            chararray = new ArrayList<>();
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == '(')
                    chararray.add(' ');
                if (temp[i] == '=')
                    chararray.add(' ');

                if ((int) temp[i] == 0)
                    chararray.add('\\');
                else
                    chararray.add(temp[i]);
            }
            chararray.add(' ');
            express = createExpressions(chararray, 0);
            past = null;
            while (express != past) {
                past = express;
                express = runner(express);
            }
            VariableMap.put("0", express);
            System.out.println(("Added " + VariableMap.get("0") + " as " + "0"));
            aInt++;
        }
        for (int j = aInt; j <= bInt; j++) {
            String currString = j + " = run succ " + (j - 1);
            System.out.println("curr " + currString);
            temp = currString.toCharArray();
            chararray = new ArrayList<>();
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == '(')
                    chararray.add(' ');
                if (temp[i] == '=')
                    chararray.add(' ');

                if ((int) temp[i] == 0)
                    chararray.add('\\');
                else
                    chararray.add(temp[i]);
            }
            chararray.add(' ');
            express = createExpressions(chararray, 0);
            past = null;
            while (express != past) {
                past = express;
                express = runner(express);
            }
            VariableMap.put(String.valueOf(j), express);
            System.out.println("testing");
            System.out.println(("Added " + VariableMap.get(settingVar) + " as " + settingVar));
        }
        settingVar= null;
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
                return i;
            }
        }
        return chararray.size();
    }

    public static Expressions deepCopy(Expressions express) {
        Expressions deepCopy = new Expressions() {
        };
        if (express instanceof Function) {
            deepCopy = new Function();
            ((Function) deepCopy).var.name = ((Function) express).var.name;
            ((Function) deepCopy).expression = deepCopy(((Function) express).expression);
            return deepCopy;
        } else if (express instanceof Applications) {
            deepCopy = new Applications();
            ((Applications) deepCopy).left = deepCopy((Expressions) ((Applications) express).left);
            ((Applications) deepCopy).right = deepCopy((Expressions) ((Applications) express).right);
            return deepCopy;
        } else {
            deepCopy = new Variables();
            ((Variables) deepCopy).name = ((Variables) express).name;
            return deepCopy;
        }

    }

    public static boolean sameExpression = false;
    public static ArrayList<String> aExpressionArray = new ArrayList<>();
    public static ArrayList<String> bExpressionArray = new ArrayList<>();

    public static void compareExpressions(Expressions a, Expressions b) {
        if (a instanceof Applications && b instanceof Applications) {
            compareExpressions((Expressions) ((Applications) a).left, (Expressions) ((Applications) b).left);
            compareExpressions((Expressions) ((Applications) a).right, (Expressions) ((Applications) b).right);
        } else if (a instanceof Variables && b instanceof Variables) {
            if ((aExpressionArray.contains(((Variables) a).name))) {
                int indexa = 0;
                for (int i = 0; i < aExpressionArray.size(); i++) {
                    if (aExpressionArray.get(i).equals(((Variables) a).name)) {
                        // System.out.println("index: "+i);
                        indexa = i;
                    }
                }
                int indexb = 0;
                for (int i = 0; i < bExpressionArray.size(); i++) {
                    if (bExpressionArray.get(i).equals(((Variables) b).name)) {
                        // System.out.println("index: "+i);
                        indexb = i;
                    }
                }
                if (indexa != indexb) {
                    // System.out.println("false1");
                    sameExpression = false;
                }
            } else {
                if (!(((Variables) a).name.equals(((Variables) b).name))) {
                    sameExpression = false;
                }
            }
        } else if (a instanceof Function && b instanceof Function) {
            aExpressionArray.add(((Function) a).var.name);
            bExpressionArray.add(((Function) b).var.name);

            compareExpressions((Expressions) ((Function) a).expression, (Expressions) ((Function) b).expression);
        } else {
            // System.out.println("false2");
            sameExpression = false;
        }
    }
}