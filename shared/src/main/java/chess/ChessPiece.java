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

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
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
    @Override
    public String toString() {
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            if (type == PieceType.QUEEN){
                return "Q";
            } else if (type == PieceType.KING) {
                return "K";
            } else if (type == PieceType.BISHOP) {
                return "B";
            } else if (type == PieceType.KNIGHT){
                return "N";
            } else if (type == PieceType.ROOK) {
                return "R";
            } else if (type == PieceType.PAWN) {
                return "P";
            }
        } else if (pieceColor == ChessGame.TeamColor.BLACK) {
            if (type == PieceType.QUEEN){
                return "q";
            } else if (type == PieceType.KING) {
                return "k";
            } else if (type == PieceType.BISHOP) {
                return "b";
            } else if (type == PieceType.KNIGHT){
                return "n";
            } else if (type == PieceType.ROOK) {
                return "r";
            } else if (type == PieceType.PAWN) {
                return "p";
            }
        } return null;
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
    public Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
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
                    ChessMove whitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.QUEEN);
                    validMoves.add(whitePawnMoves);
                    whitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.BISHOP);
                    validMoves.add(whitePawnMoves);
                    whitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.KNIGHT);
                    validMoves.add(whitePawnMoves);
                    whitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.ROOK);
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
                            ChessMove topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.QUEEN);
                            validMoves.add(topRightDiagonalWhitePawnMoves);
                            topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.BISHOP);
                            validMoves.add(topRightDiagonalWhitePawnMoves);
                            topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.KNIGHT);
                            validMoves.add(topRightDiagonalWhitePawnMoves);
                            topRightDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.ROOK);
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
                            ChessMove topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.QUEEN);
                            validMoves.add(topLeftDiagonalWhitePawnMoves);
                            topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.BISHOP);
                            validMoves.add(topLeftDiagonalWhitePawnMoves);
                            topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.KNIGHT);
                            validMoves.add(topLeftDiagonalWhitePawnMoves);
                            topLeftDiagonalWhitePawnMoves = new ChessMove(myPosition, coordinates, PieceType.ROOK);
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
                    ChessMove blackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.QUEEN);
                    validMoves.add(blackPawnMoves);
                    blackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.BISHOP);
                    validMoves.add(blackPawnMoves);
                    blackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.KNIGHT);
                    validMoves.add(blackPawnMoves);
                    blackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.ROOK);
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
                            ChessMove bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.QUEEN);
                            validMoves.add(bottomRightDiagonalBlackPawnMoves);
                            bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.BISHOP);
                            validMoves.add(bottomRightDiagonalBlackPawnMoves);
                            bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.KNIGHT);
                            validMoves.add(bottomRightDiagonalBlackPawnMoves);
                            bottomRightDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.ROOK);
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
                            ChessMove bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.QUEEN);
                            validMoves.add(bottomLeftDiagonalBlackPawnMoves);
                            bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.BISHOP);
                            validMoves.add(bottomLeftDiagonalBlackPawnMoves);
                            bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.KNIGHT);
                            validMoves.add(bottomLeftDiagonalBlackPawnMoves);
                            bottomLeftDiagonalBlackPawnMoves = new ChessMove(myPosition, coordinates, PieceType.ROOK);
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
    public Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition, int rowMax, int colMax, int rowMin, int colMin) {
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
            validMoves.addAll(getPawnMoves(board, myPosition, rowMax, colMax, rowMin, colMin));
        }
        if (type == PieceType.KNIGHT) {
            validMoves.addAll(getKnightMoves(board, myPosition, 7, 7, 1, 1));
        }
        //return valid moves
        return validMoves;
    }
}
