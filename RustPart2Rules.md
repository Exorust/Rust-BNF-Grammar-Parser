Rules for Types in RUST
==========================

Rust is a relatively new language and the documentation and Forums were not very clear as to how Name and Structural Equivalence was used.
So, we have defined our own rules for the same.

Assumptions:
------------
- We consider Name Equivalence a stricter type checking system as RUST inherently does not allow multiple declarations in one line. We have added it just for completeness.

Type Rules:
-----------
- Primitive Types: Name Equivalence and Internal Name Equivalence.
- Aliased Types: Name Equivalence, Internal Name Equivalence and Structural Equivalence. 
- Functions: Structural Equivalence.
- Struct: Structural Equivalence.
- Array: Structural Equivalence, Internal Name Equivalence.