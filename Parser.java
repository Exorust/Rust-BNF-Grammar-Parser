import java.util.*;
import java.io.*;

// Handles: Primitives, Functions, Structs, Arrays, Aliases
// NAME, INTERNAL NAME, STRUCTURAL
// syntax: type <TYPE_NAME> = <DATATYPE>;

class EquivalenceChecker{
    public static void checkNameEquivalence(   HashMap<String, ArrayList<String>> variableToType,
                                                HashMap<String,ArrayList<String>> derivedInstance) {
        
        // get a list of primitive identifiers
        ArrayList<String> idents = new ArrayList<>(variableToType.keySet());
        for (int i = 0; i < idents.size(); i++) {
            String key1 = idents.get(i);
            for (int j = 0; j < i; j++) {
                String key2 = idents.get(j);
                if ((variableToType.get(key1).get(1)).equals(variableToType.get(key2).get(1))) {
                    System.out.println(key1 + " and " + key2 + " Name Equivalent");
                } 
                else {
                    System.out.println(key1 + " and " + key2 + " NOT Name Equivalent");
                }
            }
        }
        // get a list of derived identifiers
        idents = new ArrayList<>(derivedInstance.keySet());
        for (int i = 0; i < idents.size(); i++) {
            String key1 = idents.get(i);
            for (int j = 0; j < i; j++) {
                String key2 = idents.get(j);
                if ((derivedInstance.get(key1).get(1)).equals(derivedInstance.get(key2).get(1))) {
                    System.out.println(key1 + " and " + key2 + " Name Equivalent");
                }
                else {
                    System.out.println(key1 + " and " + key2 + " NOT Name Equivalent");
                }
            }
        }
    }

    // Here Internal Name Equivalence does not mean that the two types are
    // equivalent as one possible declaration is
    // let a: i16, b: f32; which is valid (per our design) and both are internal
    // name equivalent.
    // We have created this method for completeness.
    public static void checkInternalNameEquivalence(    HashMap<String, ArrayList<String>> variableToType,
                                                        HashMap<String,ArrayList<String>> derivedInstance,
                                                        HashMap<String, ArrayList<String>> structInstances, 
                                                        HashMap<String, ArrayList<String>> arrayToType) {

        HashMap<String, ArrayList<String>> mergedMap = new HashMap<>();
        mergedMap.putAll(variableToType);
        mergedMap.putAll(derivedInstance);
        mergedMap.putAll(structInstances);
        mergedMap.putAll(arrayToType);
        ArrayList<String> idents = new ArrayList<>(mergedMap.keySet());
        for (int i = 0; i < idents.size(); i++) {
            String key1 = idents.get(i);
            for (int j = 0; j < i; j++) {
                String key2 = idents.get(j);
                if ((mergedMap.get(key1).get(0)).equals(mergedMap.get(key2).get(0))) {
                    System.out.println(key1 + " and " + key2 + " Internal Name Equivalent");
                } else {
                    System.out.println(key1 + " and " + key2 + " NOT Internal Name Equivalent");
                }
            }
        }
    }
    public static void checkStructuralEquivalence( HashMap<String, ArrayList<String>> variableToType,
                                                    HashMap<String, ArrayList<String>> derivedInstances,
                                                    HashMap<String, ArrayList<String>> structureInstances,
                                                    HashMap<String, ArrayList<ArrayList<String>>> functionToType,
                                                    HashMap<String, ArrayList<ArrayList<String>>> structToType,
                                                    HashMap<String, ArrayList<String>> arrayToType,
                                                    HashMap<String, String> derivedType
                                                    ) {

        // Array structural equivalence
        ArrayList<String> arrays = new ArrayList<>(arrayToType.keySet());
        for(int i = 1; i < arrays.size(); i++){
            for(int j = 0; j < i; j++ ){
                String key1 = arrays.get(i);
                String key2 = arrays.get(j);
                ArrayList<String> arrayData1 = arrayToType.get(key1);
                ArrayList<String> arrayData2 = arrayToType.get(key2);
                if(arrayData1.get(1).equals(arrayData2.get(1)) && arrayData1.get(2).equals(arrayData2.get(2))){
                    System.out.println(key1+" and "+key2+" STRUCTURALLY EQUIVALENT");
                }
                else{
                    System.out.println(key1+" and "+key2+" NOT STRUCTURALLY EQUIVALENT");
                }
            }
        }

        // To check structural equivalence of structures, create a nC2 boolean lookup matrix and use that for checking.
        ArrayList<String> keys = new ArrayList<>(structToType.keySet());
        boolean structEquivalence[][] = new boolean[keys.size()][];
        boolean doneFlag = false;
        if(keys.size() > 0){
            for(int i = 1; i < keys.size(); i++){
                    structEquivalence[i] = new boolean[i];
                    for(int j = 0; j < i; j++)
                        structEquivalence[i][j] = true;
            }
            // max number of times this loop can run will be the number of elements that are present
            for(int count = 0; !doneFlag && count < ((keys.size()-1)*keys.size())/2; count++){
                doneFlag = true;
                for(int i = 1; i < keys.size(); i++)
                    for(int j = 0; j < i; j++){
                        checkInternalsForEquivalence(i,j,keys.get(i), keys.get(j),structEquivalence, keys.size(),structToType.get(keys.get(i)),structToType.get(keys.get(j)),derivedType,keys);
                    }
            }
        }

        // check derived with primitive and primitive with primitive
        HashMap<String,ArrayList<String>> mergedMap = new HashMap<>();
        mergedMap.putAll(variableToType);
        mergedMap.putAll(derivedInstances);                 
        mergedMap.putAll(structureInstances);                                      
        ArrayList<String> idents = new ArrayList<>(mergedMap.keySet());
        for (int i = 0; i < idents.size(); i++) {
            String key1 = idents.get(i);
            for (int j = 0; j < i; j++) {
                String key2 = idents.get(j);
                String type1 = mergedMap.get(key1).get(1);
                String type2 = mergedMap.get(key2).get(1);
                String parentType1 = derivedType.get(type1);
                String parentType2 = derivedType.get(type2);
                if(parentType1.equals(parentType2)){                                                                // check if they are one is derived from the other
                    System.out.println(key1+" and "+key2+" STRUCTURALLY EQUIVALENT");
                }
                else if(keys.size() > 0 && structToType.containsKey(type1) && structToType.containsKey(type2)){     // check if they are structures and their structure types are defined 
                    int idx1 = keys.indexOf(type1);
                    int idx2 = keys.indexOf(type2);
                    if(idx1 > idx2 && structEquivalence[idx1][idx2]){
                        System.out.println(key1+" and "+key2+" STRUCTURALLY EQUIVALENT");
                    }
                    else if(idx2 >= idx1 && structEquivalence[idx2][idx1]){
                        System.out.println(key1+" and "+key2+" STRUCTURALLY EQUIVALENT");
                    }
                    else
                        System.out.println(key1+" and "+key2+" NOT STRUCTURALLY EQUIVALENT");
                }
                else{
                    System.out.println(key1+" and "+key2+" NOT STRUCTURALLY EQUIVALENT");
                }
            }
        }

        // Similar things applies to functions.
        keys = new ArrayList<>(functionToType.keySet());
        if(keys.size() > 0){                                // if there are functions, proceed 
            structEquivalence = new boolean[keys.size()][];
            for(int i = 1; i < keys.size(); i++){
                structEquivalence[i] = new boolean[i];
                for(int j = 0; j < i; j++)
                    structEquivalence[i][j] = true;
            }
            doneFlag = false;
            for(int count = 0;!doneFlag && count < ((keys.size()-1)*keys.size())/2; count++){
                doneFlag = true;
                for(int i = 1; i < keys.size(); i++)
                    for(int j = 0; j < i; j++){
                        checkInternalsForEquivalence(i,j,keys.get(i), keys.get(j),structEquivalence, keys.size(),functionToType.get(keys.get(i)),functionToType.get(keys.get(j)),derivedType,keys);
                    }
            }

            for(int i = 1; i < keys.size(); i++){
                for(int j = 0; j < i; j++){
                    if(structEquivalence[i][j])
                        System.out.println(keys.get(i)+" "+keys.get(j)+" STRUCTURALLY EQUIVALENT");
                    else 
                        System.out.println(keys.get(i)+" "+keys.get(j)+" NOT STRUCTURALLY EQUIVALENT");
                }
            }
        }
    }
    private static boolean checkInternalsForEquivalence(int pos1,
                                                        int pos2,
                                                        String key1, 
                                                        String key2, 
                                                        boolean[][] structEquivalence,
                                                        int keySize,ArrayList<ArrayList<String>> body1,
                                                        ArrayList<ArrayList<String>> body2,
                                                        HashMap<String,String> derivedTypes,
                                                        ArrayList<String> structTypes
                                                        ){

        boolean done = true;
        // if body is not equal... then
        if(body1.size() != body2.size()){
            structEquivalence[pos1][pos2] = false;
                    done = false;
        }
        else{
            // check equivalence of body
            for(int inBody = 0; inBody < body1.size(); inBody++){
                String structElementType1 = body1.get(inBody).get(1);
                String structElementType2 = body2.get(inBody).get(1);
                String parentType1 = derivedTypes.get(structElementType1);
                String parentType2 = derivedTypes.get(structElementType2);
                if(!parentType1.equals(parentType2)){
                    // if either is not a structure then they are not equivalent
                    if(!structTypes.contains(parentType1) || !structTypes.contains(parentType2)){
                       done = false;
                        System.out.println("1: "+structElementType1);
                        System.out.println("1: "+structElementType2);
                        System.out.println("1: "+parentType1);
                        System.out.println("1: "+parentType2);
                        structEquivalence[pos1][pos2] = false;
                    }
                    // if both are structures, the boolean array value will be the answer
                    else{
                        done = structEquivalence[pos1][pos2] = structEquivalence[structTypes.indexOf(parentType1)][structElementType1.indexOf(parentType2)];
                    }
                }
                else{
                    // derived types are equal, but they are arrays
                    if(body1.get(inBody).size() != body2.get(inBody).size()){   // primitive/derived with array
                        structEquivalence[pos1][pos2] = false;
                        done = false;
                    } 
                    else{ // both are arrays or primitives
                        if(body1.get(inBody).size() > 2){ // both are arrays
                            String arraySize1 = body1.get(inBody).get(2);
                            String arraySize2 = body2.get(inBody).get(2);
                            if(!arraySize1.equals(arraySize2)){
                                structEquivalence[pos1][pos2] = false;
                                done = false;
                            }
                        }
                    }
                }
            }
        }
        return done;
    }
}

public class Parser{
    private static ArrayList<String> datatypes;
    private static HashMap<String, ArrayList<String>> variableToType, structureInstances, derivedInstances;
    private static HashMap<String, ArrayList<ArrayList<String>>> functionToType, structToType; // basically hashmap pointing to 2d array
                                                                                               
    private static HashMap<String, ArrayList<String>> arrayToType;
    private static int internalNameGenerators = 0; // use if needed,  internal name equivalence does not exist in rust
                                                   
    private static HashMap<String, String> derivedType;

    public static void main(String args[]) {

        variableToType = new HashMap<>(); // stores primitive declarations
        functionToType = new HashMap<>(); // stores function definiion
        structToType = new HashMap<>(); // stores struct definition
        arrayToType = new HashMap<>(); // stores array definition
        derivedInstances = new HashMap<>();
        structureInstances = new HashMap<>(); // stores structure instances (checks it being a primitive instance, if
                                              // not stores here)
        derivedType = new HashMap<>(); // stores the derived type with its primitive as derived : primitive
        
        // PRIMITIVES
        datatypes = new ArrayList<String>();
        datatypes.add("i16");
        datatypes.add("i32");
        datatypes.add("u16");
        datatypes.add("u32");
        datatypes.add("f32");
        datatypes.add("bool");
        datatypes.add("char");
        datatypes.add("String");
        derivedType.put("i16","i16");
        derivedType.put("i32","i32");
        derivedType.put("u16","u16");
        derivedType.put("u32","u32");
        derivedType.put("f32","f32");
        derivedType.put("bool","bool");
        derivedType.put("char","char");
        derivedType.put("String","String");

        Scanner scanner = new Scanner(System.in);
        StringTokenizer tokenizer;
        System.out.println("Enter DECLARATIONS, enter stop to stop input");
        String sentence;
        StringBuffer buffer = new StringBuffer();
        while (scanner.hasNextLine()) {
            sentence = scanner.nextLine();
            if (sentence.equals("stop"))
                break;
            buffer.append(sentence);
            buffer.append(' ');
        }
        scanner.close();
        tokenizer = new StringTokenizer(buffer.toString(), " :,{;(");
        ArrayList<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals("let")) {
                i++;
                for (; i < tokens.size() && !tokens.get(i).equals("let") && !tokens.get(i).equals("fn")
                        && !tokens.get(i).equals("struct") && !tokens.get(i).equals("type"); i++) {

                    if (tokens.get(i).equals("mut"))
                        i++; // ignore "mut"
                    String ident = tokens.get(i);
                    i++;
                    if (i >= tokens.size())
                        break;
                    String type = tokens.get(i);
                    // if type in primitive, add here else add to struct or array
                    if (datatypes.contains(type)) {
                        ArrayList<String> params = new ArrayList<>();
                        params.add(Integer.toString(internalNameGenerators));
                        params.add(type);
                        variableToType.put(ident, params);
                    } else {
                        if (type.contains("[")) { // [ needed by array's declaration, no spaces
                            if(type.length() > 1){
                                type = type.replace("[", "");
                                i++;
                            }
                            else{
                                i++;
                                type = tokens.get(i);
                                i++;
                            }
                            String sizeOfArray = tokens.get(i);
                            sizeOfArray = sizeOfArray.replace("]", "");
                            ArrayList<String> datatypeLength = new ArrayList<>();
                            datatypeLength.add(Integer.toString(internalNameGenerators));
                            datatypeLength.add(type);
                            datatypeLength.add(sizeOfArray);
                            arrayToType.put(ident, datatypeLength);
                        } else {
                            ArrayList<String> params = new ArrayList<>();
                            params.add(Integer.toString(internalNameGenerators));
                            params.add(type);
                            if(structToType.containsKey(type))
                                structureInstances.put(ident, params); // Structural Equivalence test will be performed here
                            else
                                derivedInstances.put(ident,params);  
                        }
                    }
                }
                i--; // coz the last check will point i to "let" or "fn" or "struct"
            }
            // structs have no functions, and x calls y and y calls x implies they are
            // equivalent provided others inside are too
            else if (token.equals("struct")) {
                // structToType
                i++;
                ArrayList<ArrayList<String>> memberList = new ArrayList<>();
                String structName = tokens.get(i);
                i++;
                for (; i < tokens.size() && !(tokens.get(i)).contains("}"); i++) {
                    if (tokens.get(i).contains("}")) {
                        String temp = tokens.get(i);
                        temp = temp.replace("}", "");
                        tokens.set(i, temp);

                    }
                    ArrayList<String> eachMemberOfStruct = new ArrayList<>();
                    // invariant: variable name : type
                    eachMemberOfStruct.add(tokens.get(i)); // variable name
                    i++;
                    // type, if type has [ means it is an array, so add datatype and size to the
                    // list
                    if (tokens.get(i).contains("[")) {
                        String datatypeOfArray = tokens.get(i).replace("[", "");
                        i++;
                        String sizeOfArray = tokens.get(i).replace("]", "");
                        eachMemberOfStruct.add(datatypeOfArray);
                        eachMemberOfStruct.add(sizeOfArray);
                        memberList.add(eachMemberOfStruct);
                    } else {
                        eachMemberOfStruct.add(tokens.get(i));
                        memberList.add(eachMemberOfStruct);
                    }
                }
                structToType.put(structName, memberList);
                derivedType.put(structName,structName);
            }
            else if (token.equals("fn")) {
                // functionToType
                i++;
                ArrayList<ArrayList<String>> argsList = new ArrayList<>();
                String functionName = tokens.get(i);
                i++;
                // checks arguments
                // invariant identifier:datatype
                for (; i < tokens.size() && !(tokens.get(i)).equals(")"); i++) {
                    ArrayList<String> argument = new ArrayList<>();
                    argument.add(tokens.get(i));
                    i++;
                    if(tokens.get(i).contains("[")){
                        if(tokens.get(i).length() > 1){
                            String arrayType = tokens.get(i);
                            arrayType = arrayType.replace("[","");
                            argument.add(arrayType);
                            i++;
                        }
                        else{
                            i++;
                            String arrayType = tokens.get(i);
                            arrayType = arrayType.replace("[","");
                            argument.add(arrayType);
                            i++;
                        }
                        if (tokens.get(i).contains(")")) {
                            int length = tokens.get(i).length();
                            if (length > 1) {
                                String temp = tokens.get(i);
                                temp = temp.replace(")", "");
                                temp = temp.replace("]","");
                                System.out.println(temp);
                                tokens.set(i, temp);
                                argument.add(temp);
                                argsList.add(argument);
                                break;
                            } else {
                                i++;
                                break;
                            }
                        } else {
                            String temp = tokens.get(i);
                            temp = temp.replace("]","");
                            System.out.println(temp);
                            argument.add(temp);
                        }
                    }
                    else if (tokens.get(i).contains(")")) {
                        int length = tokens.get(i).length();
                        if (length > 1) {
                            String temp = tokens.get(i);
                            temp = temp.replace(")", "");
                            tokens.set(i, temp);
                            argument.add(temp);
                            argsList.add(argument);
                            break;
                        } else {
                            i++;
                            break;
                        }
                    } else {
                        argument.add(tokens.get(i));
                    }
                    argsList.add(argument);
                }
                i++;
                // check retrun value which is a datatype
                if (i < tokens.size() && tokens.get(i).contains("->")) {
                    i++;
                    ArrayList<String> returnList = new ArrayList<>();
                    returnList.add("returnType");
                    returnList.add(tokens.get(i));
                    i++;
                    argsList.add(returnList);
                }
                for (; i < tokens.size() && !tokens.get(i).equals("}"); i++)
                    ; // skip body of function

                functionToType.put(functionName, argsList);
            } 
            else if (token.equals("type")) {
                i++;
                String derived = tokens.get(i);
                i += 2; // skipping '='
                String derivedFrom = tokens.get(i);
                if (derivedType.containsKey(derivedFrom)) { // has a primitive associated with it
                    String primitive = derivedType.get(derivedFrom);
                    derivedType.put(derived, primitive);
                } else {
                    if (datatypes.contains(derivedFrom)) {
                        derivedType.put(derived, derivedFrom);
                    } else {
                        System.out.println("Syntax Error: primitive: " + derivedFrom + " does not exist, exiting");
                        return;
                    }
                }
            }
            internalNameGenerators++;
        }

        EquivalenceChecker.checkNameEquivalence(variableToType, derivedInstances);
        System.out.println();
        EquivalenceChecker.checkInternalNameEquivalence(variableToType, derivedInstances ,structureInstances, arrayToType);
        System.out.println();
        EquivalenceChecker.checkStructuralEquivalence(variableToType, derivedInstances ,structureInstances, functionToType, structToType, arrayToType,derivedType);
        System.out.println();
    }

}
