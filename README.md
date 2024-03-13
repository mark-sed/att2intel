# att2intel - AT&T syntax assembly converter to Intel syntax

This converter does no semantical parsing nor it tokenizes the input in any smart way. This means that it will accept incorrect programs, but in return will work on incomplete snippets and does not care for new instructions.

Main purpose of this tool is to output the assembly in Intel syntax (no `$`, `#`, `%` and reverse order of arguments) to make the assembly more readable for people who prefer and are used to this syntax (e.g. NASM programmers).

## Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

Example:
```
sbt "run code.s"
```

Or you can run it using `scala` (version 3):
```
scala src/main/scala/att2intel.scala examples/mem.s
```

## Syntaxes

Since this converter does not aim at any specific syntax it does the following conversions:
- Everything starting with `//` or `#` is taken as a comment and these symbols are replaced for `;`.
- All parenthesis (`()`) are replaced for brackets (`[]`).
- All `$` and `%` are removed.

## TODOs
- [ ] Conversion of offsets to nasm form (`movl -4(%ebp), %edi` -> `mov edi, [ebp - 4]`)
- [ ] Handle memory accesses with offsets (`movb  (%ebx, %edi, 1), %dl` -> `mov dl, 1 [ebx + edi]`)
