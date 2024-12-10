# PCore_2 Compiler

Welcome to the PCore_2 project! This repository is dedicated to developing a parser for the custom PCore language, written in Java. The current goal is to parse PCore code into an Abstract Syntax Tree (AST), though it doesn't compile to executable code yet.

## Table of Contents

1. [Introduction](#introduction)
2. [Current Status](#current-status)
3. [PCore Language Syntax](#pcore-language-syntax)
4. [Parsing Phases](#parsing-phases)
5. [Contributing](#contributing)

## Introduction

PCore is a custom programming language designed for fun and learning, with features that facilitate both stack and heap memory management. This project is an ongoing effort to build a parser for the PCore language, focusing on turning source code into an AST.

## Current Status

- **Not a full compiler yet**: Currently, the project only parses code into an AST.
- **Work in progress**: This is a hobby project, so features and capabilities are still being developed and refined.

## PCore Language Syntax

Here's an example of PCore code that the parser currently handles:

```pcore
program MemoryManagementExample;

struct Point {
    x: i32;
    y: i32;
}

func main() -> void {
    // Stack allocation
    set localPoint: Point = {x: 10, y: 20};

    // Heap allocation
    set heapPoint: ^Point = alloc(Point);
    heapPoint->x = 30;
    heapPoint->y = 40;

    // Print values
    print("Local Point:");
    printPoint(localPoint);

    print("Heap Point:");
    printPoint(*heapPoint);

    // Clean up heap memory
    free(heapPoint);

    return
}

func printPoint(p: Point) -> void {
    print("Point - X: ", p.x, ", Y: ", p.y);
}
```

## Parsing Phases

The PCore_2 compiler parses the source code into an AST through several phases. Below is an overview of these phases:

### 1. Lexical Analysis (Tokenization)

In this phase, the source code is read and broken down into a series of tokens. Each token represents a basic element of the language, such as keywords, identifiers, symbols, and literals. The lexer ensures that the tokens conform to the syntactical rules of PCore.

### 2. Syntax Analysis (Parsing)

During syntax analysis, the tokens produced by the lexer are organized into a hierarchical structure called the Abstract Syntax Tree (AST). The AST represents the grammatical structure of the source code. Each node in the AST corresponds to a construct in the PCore language, such as expressions, statements, and declarations.

### 3. Semantic Analysis (Future Phase)

Although not implemented yet, the semantic analysis phase will involve checking the AST for semantic correctness. This includes type checking, scope resolution, and ensuring that the language rules are consistently followed.

### 4. Intermediate Representation (Future Phase)

Once semantic analysis is complete, the AST may be transformed into an intermediate representation (IR). This phase is preparatory for optimization and code generation, although it's currently outside the scope of this project.

## Contributing

Contributions are welcome! If you'd like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch:
   ```sh
   git checkout -b feature/your-feature-name
   ```
3. Make your changes.
4. Commit your changes:
   ```sh
   git commit -m "Add some feature"
   ```
5. Push to the branch:
   ```sh
   git push origin feature/your-feature-name
   ```
6. Open a pull request.
