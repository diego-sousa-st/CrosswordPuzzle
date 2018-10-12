package dcc.diegosousa;

import java.util.List;

public class ActionMade {

    private Place placeUsed;
    private List<String> listUsed;
    private String wordUsed;
    private char[] lastWord;

    public ActionMade(Place placeUsed, List<String> listUsed, String wordUsed, char[] lastWord) {

        this.placeUsed = placeUsed;
        this.listUsed = listUsed;
        this.wordUsed = wordUsed;
        this.lastWord = lastWord;

    }

    public Place getPlaceUsed() {

        return placeUsed;

    }

    public List<String> getListUsed() {

        return listUsed;

    }

    public String getWordUsed() {

        return wordUsed;

    }

    public char[] getLastWord() {

        return this.lastWord;

    }

}
