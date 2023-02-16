// Reminder: you do not need an HTML page to test basic pure-client-side
// JavaScript functions. Just open Firebug and cut/paste the expressions
// into the command line at the bottom (or right) of Console. You can
// then interactively enter other expressions such as firstObject.field1,
// firstObject.field3[1], and secondObject.f1.

var firstObject =
  { field1: "string-value1",
    field2: 3,
    field3: ["a", "b", "c"]
   };
   
var someString = 
  '({ f1: "val1", f2: "val2" })';
  
var secondObject = eval(someString);

firstObject.field1;
firstObject.field2;
firstObject.field3[1];
secondObject.f1;
secondObject.f2;
