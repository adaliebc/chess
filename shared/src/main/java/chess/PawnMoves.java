package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMoves {
    ChessGame.TeamColor pieceColor;
    public PawnMoves(ChessGame.TeamColor pieceColor){
        this.pieceColor = pieceColor;
    }
    public Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();
        validMoves.addAll(getWhitePawnMoves(board, myPosition, rowMax, colMax, rowMin, colMin));
        validMoves.addAll(getBlackPawnMoves(board, myPosition, rowMax, colMax, rowMin, colMin));

        return validMoves;
    }
    public Collection<ChessMove> getWhitePawnMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        int currentCol = myPosition.getColumn();
        int currentRow = myPosition.getRow();
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            if (currentRow == 2) {
                rowMax = rowMax + 1;
            }
            // top
            while (currentRow <= rowMax) {
                currentRow++;
                ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
                ChessPiece unknown = board.getPiece(coordinates);
                if (unknown != null) {
                    break;
                }
                if (currentRow == 8) {
                    ChessMove whitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.QUEEN);
                    validMoves.add(whitePawnMoves);
                    whitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.BISHOP);
                    validMoves.add(whitePawnMoves);
                    whitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.KNIGHT);
                    validMoves.add(whitePawnMoves);
                    whitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.ROOK);
                    validMoves.add(whitePawnMoves);
                    break;
                } else {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }

            currentCol = myPosition.getColumn();
            currentRow = myPosition.getRow();
            // top right diagonal
            while (currentRow <= rowMax && currentCol <= colMax) {
                currentCol++;
                currentRow++;
                ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
                ChessPiece unknown = board.getPiece(coordinates);
                if (unknown != null) {
                    if (unknown.pieceColor != this.pieceColor) {
                        if (currentRow == 8) {
                            ChessMove topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.QUEEN);
                            validMoves.add(topRightDiagonalWhitePawnMoves);
                            topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.BISHOP);
                            validMoves.add(topRightDiagonalWhitePawnMoves);
                            topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.KNIGHT);
                            validMoves.add(topRightDiagonalWhitePawnMoves);
                            topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.ROOK);
                            validMoves.add(topRightDiagonalWhitePawnMoves);
                        } else {
                            ChessMove moves = new ChessMove(myPosition, coordinates, null);
                            validMoves.add(moves);
                        }
                    }
                    break;
                }
            }

            currentCol = myPosition.getColumn();
            currentRow = myPosition.getRow();

            //top left diagonal
            while (currentRow <= rowMax && currentCol > colMin) {
                currentCol--;
                currentRow++;
                ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
                ChessPiece unknown = board.getPiece(coordinates);
                if (unknown != null) {
                    if (unknown.pieceColor != this.pieceColor) {
                        if (currentRow == 8) {
                            ChessMove topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.QUEEN);
                            validMoves.add(topLeftDiagonalWhitePawnMoves);
                            topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.BISHOP);
                            validMoves.add(topLeftDiagonalWhitePawnMoves);
                            topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.KNIGHT);
                            validMoves.add(topLeftDiagonalWhitePawnMoves);
                            topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.ROOK);
                            validMoves.add(topLeftDiagonalWhitePawnMoves);
                        } else {
                            ChessMove moves = new ChessMove(myPosition, coordinates, null);
                            validMoves.add(moves);
                        }
                    }
                    break;
                }
            }
        }


        return validMoves;
    }
    public Collection<ChessMove> getBlackPawnMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        int currentCol = myPosition.getColumn();
        int currentRow = myPosition.getRow();
        if (pieceColor == ChessGame.TeamColor.BLACK) {
            if (currentRow == 7) {
                rowMin = rowMin - 1;
            }
            // bottom
            while (currentRow > rowMin) {
                currentRow--;
                ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
                ChessPiece unknown = board.getPiece(coordinates);
                if (unknown != null) {
                    break;
                }
                if (currentRow == 1) {
                    ChessMove blackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.QUEEN);
                    validMoves.add(blackPawnMoves);
                    blackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.BISHOP);
                    validMoves.add(blackPawnMoves);
                    blackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.KNIGHT);
                    validMoves.add(blackPawnMoves);
                    blackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.ROOK);
                    validMoves.add(blackPawnMoves);
                    break;
                } else {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                ChessMove moves = new ChessMove(myPosition, coordinates, null);
                validMoves.add(moves);
            }

            currentCol = myPosition.getColumn();
            currentRow = myPosition.getRow();
            // bottom right diagonal
            while (currentRow > rowMin && currentCol <= colMax) {
                currentCol++;
                currentRow--;
                ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
                ChessPiece unknown = board.getPiece(coordinates);
                if (unknown != null) {
                    if (unknown.pieceColor != this.pieceColor) {
                        if (currentRow == 1) {
                            ChessMove bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.QUEEN);
                            validMoves.add(bottomRightDiagonalBlackPawnMoves);
                            bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.BISHOP);
                            validMoves.add(bottomRightDiagonalBlackPawnMoves);
                            bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.KNIGHT);
                            validMoves.add(bottomRightDiagonalBlackPawnMoves);
                            bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.ROOK);
                            validMoves.add(bottomRightDiagonalBlackPawnMoves);
                        } else {
                            ChessMove moves = new ChessMove(myPosition, coordinates, null);
                            validMoves.add(moves);
                        }
                    }
                    break;
                }
            }

            currentCol = myPosition.getColumn();
            currentRow = myPosition.getRow();

            //bottom left diagonal
            while (currentRow > rowMin && currentCol > colMin) {
                currentCol--;
                currentRow--;
                ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
                ChessPiece unknown = board.getPiece(coordinates);
                if (unknown != null) {
                    if (unknown.pieceColor != this.pieceColor) {
                        if (currentRow == 1) {
                            ChessMove bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.QUEEN);
                            validMoves.add(bottomLeftDiagonalBlackPawnMoves);
                            bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.BISHOP);
                            validMoves.add(bottomLeftDiagonalBlackPawnMoves);
                            bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.KNIGHT);
                            validMoves.add(bottomLeftDiagonalBlackPawnMoves);
                            bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, ChessPiece.PieceType.ROOK);
                            validMoves.add(bottomLeftDiagonalBlackPawnMoves);
                        } else {
                            ChessMove moves = new ChessMove(myPosition, coordinates, null);
                            validMoves.add(moves);
                        }
                    }
                    break;
                }
            }
        }

        return validMoves;
    }
}
