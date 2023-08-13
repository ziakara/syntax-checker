README: by Zia Kara
____________________

This was a fun project where I had to implement a stack using the MyStackInterface. 
Then, using the defined stack, I had to code a syntax checker,which allows you to check 
a file to ensure the symbols are balanced and that there are none of the specified syntax errors. 
Specifically, to ensure { }'s, ( )'s, [ ]'s, " "'s, and /\* \*/'s are properly balanced, 
and that each one is part of a pair. It doesn't handle single line comments, literals, or the diamond operator.

This code ultimately handles the following cases:
    1. mismatched pair (an opening that doesn't have a closing, or a closing without
    and opening)
    2. a mismatched set of symbols, ex. ( and }.

Once the error is found, BalanceError returns the error above, and indicates the 
resulting error information in order to fix it.
