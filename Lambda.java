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
            for (int i = namearray.length - 1; i >= 0; i--) {
                if (namearray[i] == (';')) {
                    commentsplit = i;
                }
            }
            boolean charfound = false;
            int charpos = commentsplit;
            if (namearray[commentsplit - 1] == ' ') { // for removing spaces before the comment a____;comment removes the _____(<-spaces) 
                for (int i = commentsplit - 1; i >= 0; i--) {
                    if (charfound == false && namearray[i] == ' ') {
                        charpos = i;
                    }
                    if(namearray[i] != ' '){
                        charfound = true;
                    }
                }
            }   
            char[] temp = new char[charpos];
            System.arraycopy(namearray, 0, temp, 0, charpos);

            namearray = temp;
            System.out.println(Arrays.toString(temp));
            StringBuilder sbf = new StringBuilder("");
            sbf.append(namearray);
            name = sbf.toString();

            int numberofvariables = 0;
            if(temp.length == 0 ){
                System.out.println("bruh");
            }
            else if(temp[0] != ' '){
                numberofvariables++;
            }
            for (int i = 0; i < temp.length-1; i++) {
                if(temp[i] == ' ' && temp[i+1]!= ' '){
                    numberofvariables++;
                }
            }
            System.out.println(numberofvariables);
            Variables var = new Variables(name);

            if (!name.equals("exit")) {
                System.out.println(var.toString());
            } else {
                System.out.println("Goodbye!");
            }

        }
    }
}