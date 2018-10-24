package com.tictactoe;

public class MatrixProvider {

    private short[][] matrix = new short[3][3];

    public short[][] getCleanMatrix(){
        for (int i = 0; i < 3; i++){
            matrix[i][0] = -1;
            matrix[i][1] = -1;
            matrix[i][2] = -1;
        }

        return matrix;
    }

    public short[][] getRowOneCheckedMatrix(){
            matrix[0][0] = 1;
            matrix[0][1] = 1;
            matrix[0][2] = 1;

        return matrix;
    }

    public short[][] getRowTwoCheckedMatrix(){
        matrix[1][0] = 1;
        matrix[1][1] = 1;
        matrix[1][2] = 1;

        return matrix;
    }

    public short[][] getRowThreeCheckedMatrix(){
        matrix[2][0] = 1;
        matrix[2][1] = 1;
        matrix[2][2] = 1;

        return matrix;
    }

    public short[][] getColumnOneCheckedMatrix(){
        matrix[0][0] = 1;
        matrix[1][0] = 1;
        matrix[2][0] = 1;

        return matrix;
    }

    public short[][] getColumnTwoCheckedMatrix(){
            matrix[0][1] = 1;
            matrix[1][1] = 1;
            matrix[2][1] = 1;

        return matrix;
    }

    public short[][] getColumnThreeCheckedMatrix(){
            matrix[0][2] = 1;
            matrix[1][2] = 1;
            matrix[2][2] = 1;

        return matrix;
    }
}
