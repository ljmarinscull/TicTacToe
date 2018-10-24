package com.tictactoe;

public class MatrixValidator {

    public boolean validMatrix(short[][] matrix, int ordinal){

        if(ordinal == matrix[0][0] && ordinal == matrix[0][1] && ordinal == matrix[0][2] ) {
            return true;
        } else if(ordinal == matrix[1][0] && ordinal == matrix[1][1] && ordinal == matrix[1][2] ){
            return true;
        } else if(ordinal == matrix[2][0] && ordinal == matrix[2][1] && ordinal == matrix[2][2] ){
            return true;
        }  else  if(ordinal == matrix[0][0] && ordinal == matrix[1][0] && ordinal == matrix[2][0] ) {
            return true;
        } else if(ordinal == matrix[0][1] && ordinal == matrix[1][1] && ordinal == matrix[2][1] ){
            return true;
        } else if(ordinal == matrix[0][2] && ordinal == matrix[1][2] && ordinal == matrix[2][2] ){
            return true;
        } else if(ordinal == matrix[0][0] && ordinal == matrix[1][1] && ordinal == matrix[2][2] ){
            return true;
        } else if(ordinal == matrix[0][2] && ordinal == matrix[1][1] && ordinal == matrix[2][0] ){
            return true;
        }

        return false;
    }
}
