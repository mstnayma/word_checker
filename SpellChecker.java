import java.util.*; 
import java.io.File;
import java.io.FileNotFoundException;

public class SpellChecker implements SpellCheckerInterface{

        private HashSet<String> dictionary; 
        
        public SpellChecker(String filename){
                try {
                        dictionary = new HashSet<String>();
                        File txtfile = new File(filename);
                        Scanner input = new Scanner(txtfile);
                        while (input.hasNextLine()) {
                                String word = input.nextLine();
                                dictionary.add(word.toLowerCase().replaceAll("[^a-zA-Z0-9]",""));
                        }
                }catch (FileNotFoundException e){
                        System.out.println("file does not exist");
                }

        }

        public List<String> getIncorrectWords(String filename){
                ArrayList<String> incorrectwords = new ArrayList<String>();
                try{
                        File file = new File(filename);
                        Scanner input = new Scanner (file);
                        while (input.hasNextLine()){
                                String words = input.nextLine();   
                                String [] list = words.split("\\s+");
                                for (int i = 0; i < list.length; i++){                                                                              
                                        String newword = list[i].toLowerCase();
                                        newword = newword.replaceAll("[^a-zA-Z0-9]","");
                                        list[i] = newword;
                                        if ((!dictionary.contains (newword)) && (!newword.equals(""))){
                                                incorrectwords.add(newword);
                                        }
                                        
                                }
                        }
                }catch (FileNotFoundException e){
                        System.out.println("file does not exist");
                }
                return incorrectwords;
        }

        public Set<String> getSuggestions(String word){
                Set<String> wordSuggestions = new HashSet<>();
                String lowercaseWord = word.toLowerCase();
                String pword;
                String newWord;


                //removing punctuation and integers
                pword = lowercaseWord.replaceAll("[^a-zA-Z0-9]","");
                if (dictionary.contains(pword)){
                    wordSuggestions.add(pword);
                }
                
                //removing from each position 
                for (int i = 0; i< pword.length(); i++){
                        newWord = pword.substring(0,i) + pword.substring(i+1) ;
                        if (dictionary.contains(newWord)){
                                wordSuggestions.add(newWord);
                        }
                }

                for (int i = 0; i< pword.length(); i++){
                        newWord = pword.substring(0,i) + pword.substring(i+1,pword.length()); 
                        if (dictionary.contains(newWord)){
                                wordSuggestions.add(newWord);
                        }
                }

                //adding one character 
                char [] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                
                for (int l = 0; l<pword.length(); l++){
                    for (char letter: letters){
                        newWord = pword.substring(0,l) + letter + pword.substring(l, pword.length());
                        if (dictionary.contains(newWord)){
                                wordSuggestions.add(newWord);
                        }

                        if (l == pword.length()-1){
                                newWord = pword + letter;
                                if (dictionary.contains(newWord)){
                                        wordSuggestions.add(newWord);
                                }
                        }

                    } 

                }

                //swapping adjacent characters
                for (int i = 1; i < pword.length(); i++){
                        if (i != word.length()-1){
                                newWord = pword.substring(0, i-1) + pword.substring(i, i+1) + pword.substring(i-1, i) + pword.substring(i+1, pword.length());
                        }
                        else{
                                newWord = pword.substring(0, i-1) + pword.substring(i, i+1) + pword.substring(i-1, i);
                        }

                        if (dictionary.contains(newWord)){
                                wordSuggestions.add(newWord);
                        }

                }

                return wordSuggestions;





                
                

                
        }
        
        
        
}