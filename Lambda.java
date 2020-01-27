import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Lambda {
    public static void main(String[] args) throws IOException {
        String carrot = ">";
        String name = "";
        while (!name.equals("exit")) {
            // Enter data using BufferReader
            System.out.print(carrot);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine
            name = reader.readLine();
            char[] namearray = name.toCharArray();
            int commentsplit = namearray.length;
            for (int i = namearray.length-1; i >=0; i--) {
                if(namearray[i] == (';')){
                    commentsplit = i;
                }
            }

            char[] temp= new char[commentsplit];
            System.arraycopy(namearray, 0, temp, 0, commentsplit);
            System.out.println("commentsplit: "+commentsplit+" array:"+Arrays.toString(temp) );
            namearray = temp;
            StringBuilder sbf = new StringBuilder(""); 
            sbf.append(namearray);
            name = sbf.toString(); 
            
   
            if (!name.equals("exit")) {
                System.out.println(name);
            } else {
                System.out.println("Goodbye!");
            }

        }
    }
}