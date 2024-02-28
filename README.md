# xlang
X programming language


## tokens
- Boolean -> true, false
- Integer -> all integer value
- String  ->  double quoted string
- Variable -> name should match regex : [a-zA-Z][a-zA-Z0-9]+
- Operator -> and, or, not, +, ++ , - , *, /, < , >, = , ==, .
- Keyword -> if, else, var, struct, print

## Expression(I)
- Variable(C)
- Operator(I)
    - BinaryOperator(A)
        - AddOperator
        - ConcatOperator
    - UnaryOperator(A)
        - NotOperator
- Value(A)
    - BooleanValue
    - StringValue
    - IntegerValue

## Statement(I)
- CompositeStatement
- AssingStatement
- PrintSatement


# Examples
### code
```js
var i2 = 5
var s1 = "hello"
var s2 = " world"
var i3 = + i1 i2
print i3
var s3 = ++ s1 s2
print s3";
```
### output
```
7
hello world
```