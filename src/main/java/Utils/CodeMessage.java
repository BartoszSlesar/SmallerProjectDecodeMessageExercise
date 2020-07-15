package Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CodeMessage {
    private String codeMessage;
    private StringBuilder messageCoded;
    private DecodeMode mode;
    private Random rand;
    private Map<String, Integer> punctuation;

    public CodeMessage() {
        punctuationMap();
        messageCoded = new StringBuilder();
        rand = new Random();
    }


    public String codeMessage(String message) {
        codeMessage = message;
        mode = DecodeMode.UPPER;
        messageCoded.delete(0,messageCoded.length());
        for (int i = 0; i < message.length(); i++) {
            messageCoded.append(code("", message.charAt(i)));
        }
        messageCoded.deleteCharAt(messageCoded.length() - 1);
        return messageCoded.toString();
    }

    private String code(String t, char letter) {
        StringBuilder codedLetter = new StringBuilder();
        codedLetter.append(t);
        int seed = rand.nextInt(100) + 1;
        if (((int) letter > ('A' - 1)) && ((int) letter < ('Z' + 1))) {
            if (mode != DecodeMode.UPPER) {
                codedLetter.append(code(switchModes(seed), letter));
            } else {
                int temp = (int) letter - ('A' - 1);
                codedLetter.append(seed * 27).append(temp).append(",");
            }
        } else if ((int) letter > ('a' - 1) && (int) letter < ('z' + 1)) {
            if (mode != DecodeMode.LOWER) {
                codedLetter.append(code(switchModes(seed), letter));
            } else {
                int temp = (int) letter - ('a' - 1);
                codedLetter.append(seed * 27).append(temp).append(",");
            }

        } else {
            if (mode != DecodeMode.PUNCTUATION) {
                codedLetter.append(code(switchModes(seed), letter));
            } else {

                codedLetter.append(seed * 9).append(punctuation.get(String.valueOf(letter))).append(",");
            }

        }
        return codedLetter.toString();
    }

    private String switchModes(int seed) {
        int val = 0;
        switch (mode) {
            case UPPER:
                val = seed * 27;
                mode = DecodeMode.LOWER;
                break;
            case LOWER:
                val = seed * 27;
                mode = DecodeMode.PUNCTUATION;
                break;
            case PUNCTUATION:
                val = seed * 9;
                mode = DecodeMode.UPPER;
                break;
            default:
                break;
        }
        return val + ",";
    }


    private void punctuationMap() {
        punctuation = new HashMap<>();
        punctuation.put("!", 1);
        punctuation.put("?", 2);
        punctuation.put(",", 3);
        punctuation.put(".", 4);
        punctuation.put(" ", 5);
        punctuation.put(";", 6);
        punctuation.put("\"", 7);
        punctuation.put("'", 8);
    }

}
