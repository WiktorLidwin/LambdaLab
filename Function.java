public class Function implements Expressions, Cloneable{
    public Variables var = new Variables(null);
    public Expressions expression = null;
    public void setVar(String string){
        var.name = string;
    }public void setVar(Variables variable){
        var = variable;
    }
    public void setExpression(Object expression){
        this.expression = (Expressions) expression;
    }
    public String toString(){
        try{
        return ("(λ"+var.toString()+ "."+expression.toString()+")");
        }
        catch(Error ex){
            return "Error1";
        }
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}