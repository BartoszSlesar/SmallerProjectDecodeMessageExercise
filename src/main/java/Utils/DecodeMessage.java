package Utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecodeMessage {
    private String messageToDecode;
    private StringBuilder decodedMessage;
    private DecodeMode mode;
    private Map<Integer, String> punctuation;

    public DecodeMessage() {
        mode = DecodeMode.UPPER;
        messageToDecode = "";
        decodedMessage = new StringBuilder();
        punctuationMap();
    }

    public DecodeMessage(String messageToDecode) {
        this.messageToDecode = messageToDecode;
        decodedMessage = new StringBuilder();
        punctuationMap();
    }

    public String getMessageToDecode() {
        return messageToDecode;
    }

    public void setMessageToDecode(String messageToDecode) {
        this.messageToDecode = messageToDecode;
    }

    public String decodeMessage(String codemessage) {
        decodedMessage.delete(0,decodedMessage.length());
        mode = DecodeMode.UPPER;
        String[] messageArray = codemessage.split(",");
        for (String s : messageArray) {
            decodedMessage.append(decode(s));
        }
        return decodedMessage.toString();
    }

    private String decode(String element) {
        int elemNumValue = Integer.parseInt(element);
        String letter = "";
        int loweCaseMode = ((int) 'a') - 1;
        int upperCaseMode = ((int) 'A') - 1;
        int temp;
        switch (mode) {
            case UPPER:
                temp = elemNumValue % 27;
                if (temp == 0) {
                    mode = DecodeMode.LOWER;
                } else {
                    letter = String.valueOf((char) (temp + upperCaseMode));
                }
                break;
            case LOWER:
                temp = elemNumValue % 27;
                if (temp == 0) {
                    mode = DecodeMode.PUNCTUATION;
                } else {
                    letter = String.valueOf((char) (temp + loweCaseMode));
                }
                break;
            case PUNCTUATION:
                temp = elemNumValue % 9;
                if (temp == 0) {
                    mode = DecodeMode.UPPER;
                } else {
                    letter = punctuation.get(temp);
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Something Got Wrong with decoding",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
                break;
        }

        return letter;
    }

    private void punctuationMap() {
        punctuation = new HashMap<>();
        punctuation.put(1, "!");
        punctuation.put(2, "?");
        punctuation.put(3, ",");
        punctuation.put(4, ".");
        punctuation.put(5, " ");
        punctuation.put(6, ";");
        punctuation.put(7, "\"");
        punctuation.put(8, "'");
    }


    public String getDecodedMessage() {
        return decodedMessage.toString();
    }
}
