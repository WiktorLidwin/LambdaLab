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
        return ("(" + left.toString() + " " + right.toString() + ")");
                
    }
}
