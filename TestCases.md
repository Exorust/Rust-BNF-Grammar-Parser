Test Cases
=========

Test Case #1
-------------

To Check:
---------
### Name equivalence , Structural Equivalence and Internal Equivalence with primitives and aliased variables

```
    let x: i16;
    let y: f32;
    let a: i16,b: i32;
    type testType = i16;
    let c: testType;
```
Results:
--------

### Name Equivalence:

    b and a NOT Name Equivalent
    x and a Name Equivalent
    x and b NOT Name Equivalent
    y and a NOT Name Equivalent
    y and b NOT Name Equivalent
    y and x NOT Name Equivalent

### Internal Name Equivalence:

    a and x NOT Internal Name Equivalent
    y and x NOT Internal Name Equivalent
    y and a NOT Internal Name Equivalent
    b and x NOT Internal Name Equivalent
    b and a Internal Name Equivalent
    b and y NOT Internal Name Equivalent
    c and x NOT Internal Name Equivalent
    c and a NOT Internal Name Equivalent
    c and y NOT Internal Name Equivalent
    c and b NOT Internal Name Equivalent

### Structural Equivalence:

    a and x STRUCTURALLY EQUIVALENT
    y and x NOT STRUCTURALLY EQUIVALENT
    y and a NOT STRUCTURALLY EQUIVALENT
    b and x NOT STRUCTURALLY EQUIVALENT
    b and a NOT STRUCTURALLY EQUIVALENT
    b and y NOT STRUCTURALLY EQUIVALENT
    c and x STRUCTURALLY EQUIVALENT
    c and a STRUCTURALLY EQUIVALENT
    c and y NOT STRUCTURALLY EQUIVALENT
    c and b NOT STRUCTURALLY EQUIVALENT


Test Case #2
-------------

To Check:
---------
### Structural equivalence of arrays, functions and structures.

```
struct User {
    username: String,
    email: String,
    sign_in_count: u32,
    active: bool,
    lite: [i16,5],
}
struct User1 {
    username: String,
    email: String,
    sign_in_count: u32,
    active: bool,
    lite: [i16,5],
}
struct User2 {
    username: String,
    email: String,
    sign_in_count: u32,
    active: bool,
}
    fn function1() -> i32{}
    fn function2(arg1: i32, arg2: bool){}
    fn function3(arg1: i32, arg2: f32) -> bool{}
    let x: [i32,5];
    let y: [i32,10];
    let u1: User, u2: User1, u3: User2;
```

Results:
--------

### Name Equivalence:

    y and x NOT Internal Name Equivalent
    u1 and x NOT Internal Name Equivalent
    u1 and y NOT Internal Name Equivalent
    u2 and x NOT Internal Name Equivalent
    u2 and y NOT Internal Name Equivalent
    u2 and u1 Internal Name Equivalent
    u3 and x NOT Internal Name Equivalent
    u3 and y NOT Internal Name Equivalent
    u3 and u1 Internal Name Equivalent
    u3 and u2 Internal Name Equivalent

### Internal Name Equivalence:

    y and x NOT STRUCTURALLY EQUIVALENT
    u2 and u1 STRUCTURALLY EQUIVALENT
    u3 and u1 NOT STRUCTURALLY EQUIVALENT
    u3 and u2 NOT STRUCTURALLY EQUIVALENT
    function2 function3 NOT STRUCTURALLY EQUIVALENT
    function1 function3 NOT STRUCTURALLY EQUIVALENT
    function1 function2 NOT STRUCTURALLY EQUIVALENT


Test Case #3
-------------

To Check:
---------
- Checking all types viz. Primitive Types, Aliased Types, Functions, Struct, Arrays at once.

```
struct User {
username: String,
email: String,
sign_in_count: u64,
active: bool,
lite: [i16;5],
}
fn five() -> i32{}
fn six(arg1: i32, arg2: bool){}
let a: bool;
let c: bool,d: i16;
let mut e: User;
let f: [i32,5],g: [i32,5];
type testType = String;
let h: String,i: testType;
```
Results:
--------

### Name Equivalence:

    c and a Name Equivalent
    d and a NOT Name Equivalent
    d and c NOT Name Equivalent
    h and a NOT Name Equivalent
    h and c NOT Name Equivalent
    h and d NOT Name Equivalent

### Internal Name Equivalence:

    c and a NOT Internal Name Equivalent
    d and a NOT Internal Name Equivalent
    d and c Internal Name Equivalent
    e and a NOT Internal Name Equivalent
    e and c NOT Internal Name Equivalent
    e and d NOT Internal Name Equivalent
    f and a NOT Internal Name Equivalent
    f and c NOT Internal Name Equivalent
    f and d NOT Internal Name Equivalent
    f and e NOT Internal Name Equivalent
    g and a NOT Internal Name Equivalent
    g and c NOT Internal Name Equivalent
    g and d NOT Internal Name Equivalent
    g and e NOT Internal Name Equivalent
    g and f Internal Name Equivalent
    h and a NOT Internal Name Equivalent
    h and c NOT Internal Name Equivalent
    h and d NOT Internal Name Equivalent
    h and e NOT Internal Name Equivalent
    h and f NOT Internal Name Equivalent
    h and g NOT Internal Name Equivalent
    i and a NOT Internal Name Equivalent
    i and c NOT Internal Name Equivalent
    i and d NOT Internal Name Equivalent
    i and e NOT Internal Name Equivalent
    i and f NOT Internal Name Equivalent
    i and g NOT Internal Name Equivalent
    i and h Internal Name Equivalent

### Structural Equivalence:

    g and f STRUCTURALLY EQUIVALENT
    a and h NOT STRUCTURALLY EQUIVALENT
    i and h STRUCTURALLY EQUIVALENT
    i and a NOT STRUCTURALLY EQUIVALENT
    c and h NOT STRUCTURALLY EQUIVALENT
    c and a STRUCTURALLY EQUIVALENT
    c and i NOT STRUCTURALLY EQUIVALENT
    d and h NOT STRUCTURALLY EQUIVALENT
    d and a NOT STRUCTURALLY EQUIVALENT
    d and i NOT STRUCTURALLY EQUIVALENT
    d and c NOT STRUCTURALLY EQUIVALENT
    e and h NOT STRUCTURALLY EQUIVALENT
    e and a NOT STRUCTURALLY EQUIVALENT
    e and i NOT STRUCTURALLY EQUIVALENT
    e and c NOT STRUCTURALLY EQUIVALENT
    e and d NOT STRUCTURALLY EQUIVALENT
    five six NOT STRUCTURALLY EQUIVALENT