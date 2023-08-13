// Checks a file for syntax errors. 
// returns one of the 3 following error.
//  - MismatchError
//  - EmptyStackError
//  - NonEmptyStackError
// returns null if no error is found.

import java.util.*;
import java.io.*;

public class SymbolBalance implements SymbolBalanceInterface{
    
    private String file;
    private MyStack<Character> stack;
    
    // Prints errors given the files
    public static void main(String[] args){
        SymbolBalance sb = new SymbolBalance();
        
        for (int i = 1; i<= 6; i++){
            sb.setFile("TestFiles/Test" + i + ".java");
            System.out.println("Test " + i + ": " + sb.checkFile());
        }
    }

    public void setFile(String filename){
        file = filename;
    }
    
    public BalanceError checkFile(){
        
        try {
            File f = new File(file);
            Scanner sc = new Scanner(f);
            
            stack = new MyStack<Character>();
            
            char c; // current character
            int num = 1; // line number
            
            // scans through each line of the file
            while(sc.hasNextLine()){ 
                String line = sc.nextLine();
                
                // iterates through each character of the line
                for (int i = 0; i < line.length(); i++){ 
                    c = line.charAt(i);
                                        
                    // check for the following: {}, (), []
                    if (c == '{' || c == '(' || c == '['){
                        if (stack.isEmpty()){
                            stack.push(c);
                        }
                        else if(!stack.isEmpty()){
                            if (stack.peek() != '"' && stack.peek() != '*'){
                                stack.push(c);
                            }
                        }
                    }
                    
                    // check for quotes: ""
                    else if (c == '"'){
                        if (stack.isEmpty()){
                            stack.push(c);
                        }
                        else if(!stack.isEmpty() && stack.peek() != '*'){
                            if (stack.peek() == '"' ){
                                stack.pop();
                            }
                            else {
                                stack.push(c);
                            }         
                        }
                    }
                    
                    // check for comment blocks:/* */
                    else if (c == '*' && line.charAt(i-1) == '/'){
                        if (stack.isEmpty()){
                            stack.push(c);
                        }
                        else if(!stack.isEmpty()){
                            if (stack.peek() != '*'){
                                stack.push(c);
                            }
                        }
                    }

                    // check if the current character is a quote, 
                    // and if a comment block is not in the stack
                    else if (c == '"'  && stack.peek() != '*'){
                        if (stack.isEmpty()){
                            EmptyStackError emptystackerror = new EmptyStackError(num);
                            return emptystackerror;
                        }
                        
                        else if(!stack.isEmpty() && stack.peek() == '"'){
                            stack.pop();
                        }
       
                        else if(stack.peek() != '"' ){
                            MismatchError mismatcherror = new MismatchError(num, c, stack.pop());
                            return mismatcherror;
                        }
                    }
          
                    // check if the current character is a comment block, 
                    // and if a quote is not in the stack
                    else if (c == '*' && line.charAt(i+1) == '/' && stack.peek() != '"'){
                        if (stack.isEmpty()){
                            EmptyStackError emptystackerror = new EmptyStackError(num);
                            return emptystackerror;
                        }
                        
                        else if(!stack.isEmpty() && stack.peek() == '*'){
                            stack.pop();
                        }
                        
                        else if(stack.peek() != '*' ){
                            MismatchError mismatcherror = new MismatchError(num, c, stack.pop());
                            return mismatcherror;
                        }
                    } 
                    
                    // checks if the stack is empty, and if the scanner has no lines in the file left
                    else if (stack.peek() == null && !sc.hasNextLine()){
                        if (stack.isEmpty()){
                            EmptyStackError emptystackerror = new EmptyStackError(num);
                            return emptystackerror;
                        }
                    }
                    
                    // check if the current character is a bracket, 
                    // and if a quote or comment block is not in the stack
                    else if ((c == '}' || c == ')' || c == ']') && stack.peek() != '"' 
                             && stack.peek() != '*'){ 
                        if (stack.isEmpty()){
                            EmptyStackError emptystackerror = new EmptyStackError(num);
                            return emptystackerror;
                        }
                        
                        else if(!stack.isEmpty() && ((c == '}' && stack.peek() == '{') 
                                                     || (c == ')' && stack.peek() == '(')|| 
                                                     (c == ']' && stack.peek() == '['))){
                            stack.pop();
                        }
                        
                        else if (c == '}' && stack.peek() != '{'){
                            MismatchError mismatcherror = new MismatchError(num, c, stack.pop());
                            return mismatcherror;
                        }
                        
                        else if (c == ')' && stack.peek() != '('){
                            MismatchError mismatcherror = new MismatchError(num, c, stack.pop());
                            return mismatcherror;
                        }
                        
                        else if (c == ']' && stack.peek() != '['){
                             MismatchError mismatcherror = new MismatchError(num, c, stack.pop());
                             return mismatcherror;
                        }
                        
                        else {
                            MismatchError mismatcherror = new MismatchError(num, c, stack.pop());
                            return mismatcherror;
                        }
                    }
                } // end of for-loop
                num++;
            } // end of while loop
                           
            if (!stack.isEmpty()){
                NonEmptyStackError nonemptystackerror = new NonEmptyStackError(stack.peek(), stack.size());
                return nonemptystackerror;
            }
            
        }// end of try
        
        catch (FileNotFoundException e) {
            System.out.println("Invalid file."); 
        }
        return null;
    }// end of checkFile
}
