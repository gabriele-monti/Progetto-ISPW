package it.foodmood.domain.value;

import java.util.Objects;

public final class TablePosition {
    private final int row;
    private final int col;

    public TablePosition(int row, int col){
        if(row < 0 || col < 0){
            throw new IllegalArgumentException("Riga e colonna non possono essere negative");
        }

        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof TablePosition)) return false;
        TablePosition that = (TablePosition) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode(){
        return Objects.hash(row, col);
    }
}
