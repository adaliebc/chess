package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMoves {
    ChessGame.TeamColor pieceColor;
    public KnightMoves(ChessGame.TeamColor pieceColor){
        this.pieceColor = pieceColor;
    }
    public Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        validMoves.addAll(getTopKnightMoves(board, myPosition, rowMax, colMax, rowMin, colMin));
        validMoves.addAll(getBottomKnightMoves(board, myPosition, rowMax, colMax, rowMin, colMin));

        return validMoves;
    }
    public Collection<ChessMove> getTopKnightMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        int currentCol = myPosition.getColumn();
        int currentRow = myPosition.getRow();

        //Right Bottom
        if(currentRow > rowMin && currentCol < colMax) {
            currentRow --;
            currentCol = currentCol + 2;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove rightBottomKnightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(rightBottomKnightMoves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //Left Bottom
        if(currentRow > rowMin && currentCol > colMin +1) {
            currentRow --;
            currentCol = currentCol -2;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove leftBottomKnightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(leftBottomKnightMoves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //Bottom Right
        if(currentRow > rowMin && currentCol <= colMax) {
            currentRow = currentRow - 2;
            currentCol++;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove bottomRightKnightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(bottomRightKnightMoves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //Bottom Left
        if(currentRow > rowMin + 1 && currentCol > colMin) {
            currentRow = currentRow - 2;
            currentCol--;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove bottomLeftKnightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(bottomLeftKnightMoves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }

        return validMoves;
    }
    public Collection<ChessMove> getBottomKnightMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        int currentCol = myPosition.getColumn();
        int currentRow = myPosition.getRow();

        //Top Right
        if(currentRow < rowMax && currentCol <= colMax) {
            currentRow = currentRow + 2;
            currentCol++;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove topRightKnightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(topRightKnightMoves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //Top Left
        if(currentRow < rowMax && currentCol > colMin) {
            currentRow = currentRow + 2;
            currentCol--;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove topLeftKnightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(topLeftKnightMoves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //Right Top
        if(currentRow <= rowMax && currentCol < colMax) {
            currentRow ++;
            currentCol = currentCol + 2;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }

        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();
        //Left Top
        if(currentRow <= rowMax && currentCol > colMin +1) {
            currentRow ++;
            currentCol = currentCol -2;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove leftTopKnightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(leftTopKnightMoves);
                }
            }else {
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }
        }
        return validMoves;
    }
}
