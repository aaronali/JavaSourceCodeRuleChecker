# JavaSourceCodeRuleChecker
Checks source code against custom java rules

This is a tool used to help find errors in code. You can create your own rules by extending ARule and implement the getResults methods from IRule.
The get results method use an array of Objects as arguments so you can build your custom rule to use any objects you like.

The getResults method should return a hashmap with the source file as the key and  an array of ErrorLines as the value. ErrorLine is simply a class with a line number
and a message , usually the line itself is the message, but you can change it to whatever you like.

In the main class you can add the rules to the rulesToCheck array list and run the application. Ensure to change the path location of the projects
to match you local drives location.


