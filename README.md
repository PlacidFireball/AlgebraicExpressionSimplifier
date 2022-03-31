# Algebraic Expression Simplifier
### Written by Jared Weiss to learn some Scala!

## Problem Statement
A simplified algebraic expression is an expression which has been reduced to a simpler, more compact form without changing the expression's value. To a great extent, this largely involves collecting and combining 'like' terms.

Given an algebraic expression, reduce the expression to a simplified form meeting the following criteria:

All terms are in descending order of the power of variable .
Coefficients should be concatenated immediately to the left of your variable (e.g.:  should be printed as 5x).
There are exactly  characters between any  consecutive terms of the expression: a space, followed by a  or  sign, followed by another space.
In case there is no operator between expressions, assume that it implies multiplication. e.g. (5x+2)(x+2) should be treated as (5x+2)*(x+2), 5(x+1) should be treated as 5*(x+1).
The simplified expression must not contain any parentheses.
-coefficients and -powers are implied; if the coefficient or power of a certain  term is , do not output  (e.g.:  or  simplifies to , and  simplifies to ).
Do not print the powers of  having a coefficient of  (e.g.: output 5x^2 - 3, not 5x^2 + 0x - 3).
Input Format

The first line contains an integer, n, denoting the number of test cases.
The  subsequent lines of test cases each contain an expression that you must reduce.

Constraints

Each expression will only use a single variable, , and may contain parentheses ((,)), addition (+), subtraction (-), multiplication (*), division (/), and exponentiation (^) symbols. The role of exponentation symbols will be limited to representing powers of  or integers. You will not encounter terms such as . You may encounter terms like 3^(1+4) and so on.
There may be one or more spaces between any consecutive terms, operators, or operands (so you must account for and remove these in your code).
No divisor will contain a term involving , and all divisors are integers.
All coefficients in the final expression are integers (e.g.: ). You will not encounter something like  or .
There may be multiple levels of nested parentheses.
The original expression will not contain more than  characters (including spaces).
The expression will not evaluate to a polynomial of an order higher than .
You will not encounter an integer exeeding  either while parsing the original expression or in the final coefficients of your simplified expressions.
Expressions not containing a variable () simply require you to calculate the expression's result.
Output Format

For each test case, print the simplified expression on a new line. Your reduced expression should meet the criteria set forth in the Problem Statement above.

Sample Input
6\
10x + 2x - (3x + 6)/3\
18*(2x+2) - 5\
((9x + 81)/3 + 27)/3  - 2x\
18x + (12x + 10)*(2x+4)/2 - 5x   
(2x+5) * (x*(9x + 81)/3 + 27)/(1+1+1) - 2x  
(2x+5) * (x*(9x^3 + 81)/3 + 27)/(1+1+1)  - 2x  

Sample Output \
11x - 2 \
36x + 31 \
-x + 18 \
12x^2 + 47x + 20\
2x^3 + 23x^2 + 61x + 45 \
2x^5 + 5x^4 + 18x^2 + 61x + 45
Explanation

Observe that the original expressions have been expanded and their like terms collected, thus resulting in the printed simplified expressions.