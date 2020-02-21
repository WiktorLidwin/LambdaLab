public class Function implements Expressions{
    public Variables var = new Variables(null);
    public Expressions expression = null;
    public void setVar(String string){
        var.name = string;
    }
    public void setExpression(Object expression){
        this.expression = (Expressions) expression;
    }
    
}