    public class Applications implements Expressions, Cloneable{
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
        try{
        return ("(" + left.toString() + " " + right.toString() + ")");
        }
        catch(Error ex){
            return "error2";
        }
        
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
