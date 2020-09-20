# RParser
## Overview

The purpose is to be able to parse a R program and then generate the abstract syntax tree (AST).

The parser is generated from a JavaCC grammar, whose productions have been derived from the R grammar file. Later I have
tuned those productions to eliminate left recursions and to adapt them to JavaCC.

## How to use it

1. Pull the repo.
2. Open a console at the repo source dir.
3. Run ```mvn install```. The parser class (R) should have been generated at this point.
4. Now in your project, invoke it this way:
```
public static void main(String[] args) {
  try {
    File file = new File(args[0]);  // suppose you are passing the file path as the first argument
    InputStream inputStream = new FileInputStream(file);
    R rParser = new R(inputStream);
    SimpleNode root = rParser.parse();
    // Now do something useful with the AST :)

  } catch (Exception e) {
    System.out.println("Parser error");
    e.printStackTrace();
  }
}
```

## Performance

In my tests I've observed parsing times between 5-10ms per file.


## Contributing

- Fix typos.
- Fix grammar bugs.
- Add more test cases.
- Any help would be appreciated :).


## Contact
José Antonio Garcel (garcel.developer@gmail.com)

Sept 13 2020