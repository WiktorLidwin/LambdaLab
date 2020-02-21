    public class Applications implements Expressions{
    Object left = null;
    Object right = null;
    public void setLeft(Expressions left){
        this.left = left;
    }
    public void setRight(Expressions right){
        this.right = right;
    }
    public String toString(){
        if(right == null && left == null){
            return "(null null)";
        }
        if(right == null){
            //return ( left.toString()  );
            return ("(" + left.toString() + " null)");
        }
        if(left == null){
            //return (right.toString() );
            return ("(null " + right.toString() + ")");
        }
        return ("(" + left.toString() + " " + right.toString() + ")");
        
    }
}
