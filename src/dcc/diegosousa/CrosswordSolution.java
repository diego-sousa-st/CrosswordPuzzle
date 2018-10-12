package dcc.diegosousa;

import java.util.*;

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
    /**
     * K - amount of words
     * V - Map with k - words' size and V - list of words with size K
     */
    private Map<Integer, Map<Integer, List<String>>> wordsByAmount;

    public CrosswordSolution(String[] crossword, String words) {

        this.possiblePlaces = new HashMap<>();
        this.actionMades = new Stack<>();
        this.wordsByAmount = new HashMap<>();
        this.setCrossword(crossword);
        this.setWords(words);
        this.findPossiblePlaces();
        this.organizeWordsByAmount();

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

                if ( this.crossword[row][col] == '-' ) {

                    int posLeft = col - 1;
                    int posRight = col + 1;
                    int posTop = row - 1;
                    int posBottom = row + 1;
                    int sizeWord = 1;

                    //size 1 case

                    // horizontal case
                    if( posRight < this.SIZE ) {

                        if( posLeft < 0 || this.crossword[row][posLeft] == '+') {

                            char actualSignal = this.crossword[row][col+sizeWord];

                            while (actualSignal != '+') {

                                sizeWord++;

                                if(col+sizeWord < this.SIZE) {

                                    actualSignal = this.crossword[row][col+sizeWord];

                                } else {

                                    actualSignal = '+';

                                }

                            }

                            if(sizeWord > 1) {

                                Place place = new Place(row, col, Direction.Horizontal);

                                this.putPlaceIntoPossiblePlaces(sizeWord, place);

                            }

                        }

                    }

                    sizeWord = 1;

                    // vertical case
                    if( posBottom < this.SIZE ) {

                        if( posTop < 0 || this.crossword[posTop][col] == '+') {

                            char actualSignal = this.crossword[row+sizeWord][col];

                            while (actualSignal != '+') {

                                sizeWord++;

                                if(row+sizeWord < this.SIZE) {

                                    actualSignal = this.crossword[row+sizeWord][col];

                                } else {

                                    actualSignal = '+';

                                }

                            }

                            if(sizeWord > 1) {

                                Place place = new Place(row, col, Direction.Vertical);

                                this.putPlaceIntoPossiblePlaces(sizeWord, place);

                            }

                        }

                    }

                }

            }

        }

    }

    private void putPlaceIntoPossiblePlaces(int sizeWord, Place place) {

        if(!this.possiblePlaces.containsKey(sizeWord)) {

            this.possiblePlaces.put(sizeWord, new ArrayList<>());

        }

        List<Place> places = this.possiblePlaces.get(sizeWord);
        places.add(place);

    }

    private void organizeWordsByAmount() {

        Map<Integer, List<String>> wordsByLength = new HashMap<>();

        for ( int i = 0; i < this.words.size(); i++) {

            String word = this.words[i];
            int sizeWord = word.length();

            if(!wordsByLength.containsKey(sizeWord)) {

                wordsByLength.put(sizeWord, new ArrayList<>());

            }

            List<String> words = wordsByLength.get(sizeWord);
            words.add(word);

        }

        wordsByLength.forEach((sizeWord, list) -> {

            if(!this.wordsByAmount.containsKey(list.size())) {

                this.wordsByAmount.put(list.size(), new HashMap<>());

            }

            Map<Integer, List<String>> map = this.wordsByAmount.get(list.size());
            map.put(sizeWord, list);

        });

    }

    public String[] solve() {

        List<Integer> keys = new ArrayList<>(this.wordsByAmount.size());
        keys.addAll(this.wordsByAmount.keySet());
        Collections.sort(keys);

        this.solveResursively();

        return this.crosswordCompleted;

    }

    private void solveResursively() {

        if(this.words.length == 0) {

            this.generateResult();
            return;

        }

        String word = this.words[0];

        for(Place place : possiblePlaces.get(word.length())) {

            this.writeWord(place, word);
            this.solveResursively();
            this.restoreAction();

        }

        this.words[position] = word;

    }

    private void generateResult() {

        // TODO gerar matriz com os resultados

    }

    private boolean writeWord(Place place, String word) {

        char[] charsWord = word.toCharArray();

        if(place.getDirection().equals(Direction.Vertical)) {

            for (int i = 0; i < charsWord.length; i++) {

                if(this.crossword[place.getRow()+i][place.getCol()] == '-' || this.crossword[place.getRow()+i][place.getCol()] == charsWord[i]) {

                    this.crossword[place.getRow()+i][place.getCol()] = charsWord[i];

                } else {

                    return false;

                }

            }

        } else if (place.getDirection().equals(Direction.Horizontal)) {

            for (int i = 0; i < charsWord.length; i++) {

                if(this.crossword[place.getRow()][place.getCol()+i] == '-' || this.crossword[place.getRow()][place.getCol()+i] == charsWord[i]) {

                    this.crossword[place.getRow()][place.getCol()+i] = charsWord[i];

                } else {

                    return false;

                }

            }

        }

        return true;

    }

    private boolean makeAction(Place place, String word, List<String> listUsed) {

        this.makeActionBackup(place, word, listUsed);
        return this.writeWord(place, word);

    }

    private void makeActionBackup(Place place, String word, List<String> listUsed) {

        char[] charsCrosswordPlace = new char[word.length()];


        if(place.getDirection().equals(Direction.Vertical)) {

            for (int i = 0; i < word.length(); i++) {

                charsCrosswordPlace[i] = this.crossword[place.getRow()+i][place.getCol()];

            }

        } else if (place.getDirection().equals(Direction.Horizontal)) {

            for (int i = 0; i < word.length(); i++) {

                charsCrosswordPlace[i] = this.crossword[place.getRow()][place.getCol()+i];

            }

        }

        ActionMade actionMade = new ActionMade(place, listUsed, word, charsCrosswordPlace);
        this.actionMades.push(actionMade);

    }

    private void restoreAction() {

    }

    private void setWords(String words) {

        this.words = words.split(";");

    }

}
