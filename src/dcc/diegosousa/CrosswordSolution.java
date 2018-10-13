package dcc.diegosousa;

import java.util.*;

/**
 * @author diego
 */
public class CrosswordSolution {

    private int SIZE;

    private List<String> words;
    private char[][] crossword;
    private String[] crosswordCompleted;
    /**
     * K - words' size
     * V - list of possible places
     */
    private Map<Integer, List<Place>> possiblePlaces;
    private Stack<ActionMade> actionMades;

    public CrosswordSolution(String[] crossword, String words) {

        this.possiblePlaces = new HashMap<>();
        this.actionMades = new Stack<>();
        this.setCrossword(crossword);
        this.setWords(words);
        this.findPossiblePlaces();

    }

    private void setCrossword(String[] crossword) {

        this.SIZE = crossword.length;
        this.crossword = new char[this.SIZE][this.SIZE];

        for (int row = 0; row < this.SIZE; row++) {

            this.crossword[row] = crossword[row].toCharArray();

        }

    }

    private void findPossiblePlaces() {

        for (int row = 0; row < this.SIZE; row++) {

            for (int col = 0; col < this.SIZE; col++) {

                if (this.crossword[row][col] == '-') {

                    int posLeft = col - 1;
                    int posRight = col + 1;
                    int posTop = row - 1;
                    int posBottom = row + 1;
                    int sizeWordThread1 = 1;
                    int sizeWordThread2 = 1;

                    this.findPossiblePlacesSizeOne(sizeWordThread1, posLeft, posRight, posTop, posBottom, row, col);
                    this.findPossiblePlacesHorizontal(sizeWordThread1, posLeft, posRight, row, col);
                    this.findPossiblePlacesVertical(sizeWordThread2, posBottom, posTop, row, col);

                }

            }

        }

    }

    private void findPossiblePlacesHorizontal(int sizeWord, int posLeft, int posRight, int row, int col) {

        if (posRight < this.SIZE) {

            if (posLeft < 0 || this.crossword[row][posLeft] == '+') {

                char actualSignal = this.crossword[row][col + sizeWord];

                while (actualSignal != '+') {

                    sizeWord++;

                    if (col + sizeWord < this.SIZE) {

                        actualSignal = this.crossword[row][col + sizeWord];

                    } else {

                        actualSignal = '+';

                    }

                }

                if (sizeWord > 1) {

                    Place place = new Place(row, col, Direction.Horizontal);

                    this.putPlaceIntoPossiblePlaces(sizeWord, place);

                }

            }

        }

    }

    private void findPossiblePlacesVertical(int sizeWord, int posBottom, int posTop, int row, int col) {

        if (posBottom < this.SIZE) {

            if (posTop < 0 || this.crossword[posTop][col] == '+') {

                char actualSignal = this.crossword[row + sizeWord][col];

                while (actualSignal != '+') {

                    sizeWord++;

                    if (row + sizeWord < this.SIZE) {

                        actualSignal = this.crossword[row + sizeWord][col];

                    } else {

                        actualSignal = '+';

                    }

                }

                if (sizeWord > 1) {

                    Place place = new Place(row, col, Direction.Vertical);

                    this.putPlaceIntoPossiblePlaces(sizeWord, place);

                }

            }

        }

    }

    private void findPossiblePlacesSizeOne(int sizeWord, int posLeft, int posRight, int posTop, int posBottom, int row, int col) {

        if (!(posRight < this.SIZE)) {

            if (!(posTop >= 0)) {

                if ((this.crossword[posBottom][col] == '+') && (this.crossword[row][posLeft] == '+')) {

                    this.setPossiblePlaceSizeOne(row, col, sizeWord);

                }

            } else if (!(posBottom < this.SIZE)) {

                if ((this.crossword[posTop][col] == '+') && (this.crossword[row][posLeft] == '+')) {

                    this.setPossiblePlaceSizeOne(row, col, sizeWord);

                }

            } else {

                if ((this.crossword[posBottom][col] == '+') && (this.crossword[row][posLeft] == '+') &&
                        (this.crossword[posTop][col] == '+')) {

                    this.setPossiblePlaceSizeOne(row, col, sizeWord);

                }

            }

        } else if (!(posTop >= 0)) {

            if (!(posLeft >= 0)) {

                if ((this.crossword[posBottom][col] == '+') && (this.crossword[row][posRight] == '+')) {

                    this.setPossiblePlaceSizeOne(row, col, sizeWord);

                }

            } else {

                if ((this.crossword[posBottom][col] == '+') && (this.crossword[row][posRight] == '+') &&
                        (this.crossword[row][posLeft] == '+')) {

                    this.setPossiblePlaceSizeOne(row, col, sizeWord);

                }

            }

        } else if (!(posLeft >= 0)) {

            if (!(posBottom < this.SIZE)) {

                if ((this.crossword[posTop][col] == '+') && (this.crossword[row][posRight] == '+')) {

                    this.setPossiblePlaceSizeOne(row, col, sizeWord);

                }

            } else {

                if ((this.crossword[posTop][col] == '+') && (this.crossword[row][posRight] == '+') &&
                        (this.crossword[posBottom][col] == '+')) {

                    this.setPossiblePlaceSizeOne(row, col, sizeWord);

                }

            }

        } else if (!(posBottom < this.SIZE)) {

            if ((this.crossword[posTop][col] == '+') && (this.crossword[row][posRight] == '+') &&
                    (this.crossword[row][posLeft] == '+')) {

                this.setPossiblePlaceSizeOne(row, col, sizeWord);

            }

        } else if ((this.crossword[posBottom][col] == '+') && (this.crossword[posTop][col] == '+') &&
                (this.crossword[row][posRight] == '+') && (this.crossword[row][posLeft] == '+')) {

            this.setPossiblePlaceSizeOne(row, col, sizeWord);

        }

    }

    private void setPossiblePlaceSizeOne(int row, int col, int sizeWord) {

        // Direction here doesn't matter
        Place place = new Place(row, col, Direction.Horizontal);
        this.putPlaceIntoPossiblePlaces(sizeWord, place);

    }

    private void putPlaceIntoPossiblePlaces(int sizeWord, Place place) {

        if (!this.possiblePlaces.containsKey(sizeWord)) {

            this.possiblePlaces.put(sizeWord, new ArrayList<>());

        }

        List<Place> places = this.possiblePlaces.get(sizeWord);
        places.add(place);

    }

    public String[] solve() {

        this.solveRecursively();

        return this.crosswordCompleted;

    }

    private boolean solveRecursively() {

        if (this.words.size() == 0) {

            this.generateResult();

            return true;

        }

        String word = this.words.remove(0);
        List<Place> places = possiblePlaces.get(word.length());

        for (Place place : places) {

            if (place.isAvailable()) {

                place.setAvailable(false);
                boolean sucessAction = this.makeAction(place, word);
                if (sucessAction) {

                    boolean solved = this.solveRecursively();

                    if (solved) {

                        return true;

                    } else {

                        this.restoreAction();
                        place.setAvailable(true);

                    }

                } else {

                    this.restoreAction();
                    place.setAvailable(true);

                }

            }

        }

        this.words.add(word);
        return false;

    }

    private void generateResult() {

        this.crosswordCompleted = new String[this.SIZE];

        for (int row = 0; row < this.SIZE; row++) {

            StringBuilder line = new StringBuilder();

            for (int col = 0; col < this.SIZE; col++) {

                line.append(this.crossword[row][col]).append(" ");

            }

            this.crosswordCompleted[row] = line.toString();

        }

    }

    private boolean writeWord(Place place, String word) {

        char[] charsWord = word.toCharArray();

        if (place.getDirection().equals(Direction.Vertical)) {

            for (int i = 0; i < charsWord.length; i++) {

                if (this.crossword[place.getRow() + i][place.getCol()] == '-' || this.crossword[place.getRow() + i][place.getCol()] == charsWord[i]) {

                    this.crossword[place.getRow() + i][place.getCol()] = charsWord[i];

                } else {

                    return false;

                }

            }

        } else if (place.getDirection().equals(Direction.Horizontal)) {

            for (int i = 0; i < charsWord.length; i++) {

                if (this.crossword[place.getRow()][place.getCol() + i] == '-' || this.crossword[place.getRow()][place.getCol() + i] == charsWord[i]) {

                    this.crossword[place.getRow()][place.getCol() + i] = charsWord[i];

                } else {

                    return false;

                }

            }

        }

        return true;

    }

    private boolean makeAction(Place place, String word) {

        this.makeActionBackup(place, word);

        return this.writeWord(place, word);

    }

    private void makeActionBackup(Place place, String word) {

        char[] charsCrosswordPlace = new char[word.length()];

        if (place.getDirection().equals(Direction.Vertical)) {

            for (int i = 0; i < word.length(); i++) {

                charsCrosswordPlace[i] = this.crossword[place.getRow() + i][place.getCol()];

            }

        } else if (place.getDirection().equals(Direction.Horizontal)) {

            for (int i = 0; i < word.length(); i++) {

                charsCrosswordPlace[i] = this.crossword[place.getRow()][place.getCol() + i];

            }

        }

        ActionMade actionMade = new ActionMade(place, charsCrosswordPlace);
        this.actionMades.push(actionMade);

    }

    private void restoreAction() {

        ActionMade actionMade = this.actionMades.pop();

        Place place = actionMade.getPlaceUsed();

        char[] charsCrosswordReplaced = actionMade.getLastWord();

        if (place.getDirection().equals(Direction.Vertical)) {

            for (int i = 0; i < charsCrosswordReplaced.length; i++) {

                this.crossword[place.getRow() + i][place.getCol()] = charsCrosswordReplaced[i];

            }

        } else if (place.getDirection().equals(Direction.Horizontal)) {

            for (int i = 0; i < charsCrosswordReplaced.length; i++) {

                this.crossword[place.getRow()][place.getCol() + i] = charsCrosswordReplaced[i];

            }

        }

    }

    private void setWords(String words) {

        String[] aux = words.split(";");
        this.words = new ArrayList<>();

        Collections.addAll(this.words, aux);

    }

}
