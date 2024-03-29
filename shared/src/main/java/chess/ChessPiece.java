package chess;

import java.util.*;


import static java.util.Collections.addAll;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor = null;
    ChessPiece.PieceType type = null;
    PawnMoves pawnMoves;
    KnightMoves knightMoves;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        pawnMoves = new PawnMoves(pieceColor);
        knightMoves = new KnightMoves(pieceColor);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN;

    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }
    public String toString() {
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            if (type == PieceType.QUEEN){
                return " ♕ ";
            } else if (type == PieceType.KING) {
                return " ♔ ";
            } else if (type == PieceType.BISHOP) {
                return " ♗ ";
            } else if (type == PieceType.KNIGHT){
                return " ♘ ";
            } else if (type == PieceType.ROOK) {
                return " ♖ ";
            } else if (type == PieceType.PAWN) {
                return " ♙ ";
            }
        } else if (pieceColor == ChessGame.TeamColor.BLACK) {
            if (type == PieceType.QUEEN){
                return " ♛ ";
            } else if (type == PieceType.KING) {
                return " ♚ ";
            } else if (type == PieceType.BISHOP) {
                return " ♝ ";
            } else if (type == PieceType.KNIGHT){
                return " ♞ ";
            } else if (type == PieceType.ROOK) {
                return " ♜ ";
            } else if (type == PieceType.PAWN) {
                return " ♟ ";
            }
        } return " \u2003 ";
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    public Collection<ChessMove> getDiagonalMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        int currentCol = myPosition.getColumn();
        int currentRow = myPosition.getRow();
        // top right diagonal
        while (currentRow <= rowMax && currentCol <= colMax) {
            currentCol++;
            currentRow++;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                break;
            }
            ChessMove diagonalMoves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(diagonalMoves);
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
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                break;
            }
            ChessMove moves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(moves);
        }

        //bottom left diagonal
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();

        while (currentRow > rowMin && currentCol > colMin) {
            currentCol--;
            currentRow--;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                break;
            }
            ChessMove moves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(moves);
        }

        //bottom right diagonal
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();

        while (currentRow > rowMin && currentCol <= colMax) {
            currentCol++;
            currentRow--;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                break;
            }
            ChessMove moves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(moves);
        }

        return validMoves;
    }

    public Collection<ChessMove> getStraightMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();

        int currentCol = myPosition.getColumn();
        int currentRow = myPosition.getRow();
        // top
        while (currentRow <= rowMax) {
            currentRow++;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove straightMoves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(straightMoves);
                }
                break;
            }
            ChessMove moves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(moves);
        }

        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();

        //right
        while (currentCol <= colMax) {
            currentCol++;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                break;
            }
            ChessMove moves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(moves);
        }

        //bottom
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();

        while (currentRow > rowMin) {
            currentRow--;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                break;
            }
            ChessMove moves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(moves);
        }

        //left
        currentCol = myPosition.getColumn();
        currentRow = myPosition.getRow();

        while (currentCol > colMin) {
            currentCol--;
            ChessPosition coordinates = new ChessPosition(currentRow, currentCol);
            ChessPiece unknown = board.getPiece(coordinates);
            if (unknown != null) {
                if (unknown.pieceColor != this.pieceColor) {
                    ChessMove moves = new ChessMove(myPosition, coordinates, null);
                    validMoves.add(moves);
                }
                break;
            }
            ChessMove moves = new ChessMove(myPosition, coordinates, null);
            validMoves.add(moves);
        }

        return validMoves;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new HashSet<ChessMove>();


        if (type == PieceType.BISHOP) {
            validMoves.addAll(getDiagonalMoves(board, myPosition, 7, 7, 1, 1));
        } else if (type == PieceType.KING) {
            int rowMax = myPosition.getRow();
            int colMax = myPosition.getColumn();
            int rowMin = myPosition.getRow();
            int colMin = myPosition.getColumn();
            if (rowMax > 7) {
                rowMax = 7;
            }
            if (colMax > 7) {
                colMax = 7;
            }
            if (rowMin > 1) {
                rowMin--;
            } else {
                rowMin = 1;
            }
            if (colMin > 1) {
                colMin--;
            } else {
                colMin = 1;
            }
            validMoves.addAll(getDiagonalMoves(board, myPosition, rowMax, colMax, rowMin, colMin));
            validMoves.addAll(getStraightMoves(board, myPosition, rowMax, colMax, rowMin, colMin));
        }
        if (type == PieceType.QUEEN) {
            validMoves.addAll(getDiagonalMoves(board, myPosition, 7, 7, 1, 1));
            validMoves.addAll(getStraightMoves(board, myPosition, 7, 7, 1, 1));
        }
        if (type == PieceType.ROOK) {
            validMoves.addAll(getStraightMoves(board, myPosition, 7, 7, 1, 1));
        }
        if (type == PieceType.PAWN) {
            int rowMax = myPosition.getRow();
            int colMax = myPosition.getColumn();
            int rowMin = myPosition.getRow();
            int colMin = myPosition.getColumn();
            if (rowMax > 7) {
                rowMax = 7;
            }
            if (colMax > 7) {
                colMax = 7;
            }
            if (rowMin > 1) {
                rowMin--;}
            if (colMin > 1) {
                colMin--;
            } else {
                colMin = 1;
            }
            validMoves.addAll(pawnMoves.getPawnMoves(board, myPosition, rowMax, colMax, rowMin, colMin));
        }
        if (type == PieceType.KNIGHT) {
            validMoves.addAll(knightMoves.getKnightMoves(board, myPosition, 7, 7, 1, 1));
        }
        //return valid moves
        return validMoves;
    }
}
