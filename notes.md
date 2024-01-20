# My Notes

## 1/16/24 

You usually need a class, except when you have:
public status void main(String[] args)
- doesn't necessarily matter where you call the status void, but you should call it in a place that makes sense.

JDK is all of the development code (Java Development Kit)
- like stds in cpp
- oracle is mean, use amazon's

When you are just coding, not creating an object, use public status void
- like in chess moves to develop the validMoves

File name has to match class name

if in different package, you have to import the code(as long as it is public)
- folder is package name, same file same class

Enum is key word, gives you the options

Constructor creates new object, setter can change it (can be used any time)

Object
- to String(): string
- equals() : boolean
  - == works with hashcodes. Are they the same object?
  - equals() checks the value
- hashcode() : int (automatically created)
- clone() : Object (copy constructors are better and more common)
- wait() 
- notify()


record PetRecord(int id, String name, String type) {}
- shortcut syntax
- adds getters not setters
- creates data that is not immutable