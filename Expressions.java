public class Expressions{
    String expression;
    Object left;
    Object right;
    public Expressions(String expression){
        this.expression = expression.trim();
        System.out.println("here");
        System.out.println(this.expression);
        //toTree();
    }
    public String toTree(){
        char[] expressionArray = this.expression.toCharArray();
        int spaceIndex = 0;
        for (int i = 0; i < expressionArray.length; i++) {
            if(expressionArray[i] == ' '){
                spaceIndex = i;
            }
        }
        char[] leftArray = new char[spaceIndex-1];
        char[] rightArray = new char[expressionArray.length - spaceIndex-1];
        System.arraycopy(expressionArray, 1, leftArray, 0, spaceIndex-1);
        System.arraycopy(expressionArray, spaceIndex, rightArray, 0, expressionArray.length - spaceIndex-1);
        //StringBuilder sbf = new StringBuilder();

        this.left = leftArray;
        this.right = rightArray;
        System.out.println(leftArray);
        System.out.println(rightArray);
        


        return " ";
    }
    public void setLeft(String left){
        this.left = left;
    }
    public void setRight(String right){
        this.right = right;
    }
    public String toString(){
        Object temp = "hi ";
        System.out.println(temp);
        return ("(" + this.left.toString() + " " + this.right.toString() + ")");
    }
}