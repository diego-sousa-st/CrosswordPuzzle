package dcc.diegosousa;

public class ActionMade {

    private Place placeUsed;
    private char[] lastWord;

    public ActionMade(Place placeUsed, char[] lastWord) {

        this.placeUsed = placeUsed;
        this.lastWord = lastWord;

    }

    public Place getPlaceUsed() {

        return placeUsed;

    }

    public char[] getLastWord() {

        return this.lastWord;

    }

}
