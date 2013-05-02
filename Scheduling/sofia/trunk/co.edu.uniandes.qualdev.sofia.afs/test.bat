
@echo off

SET CLASSTEMP=%CLASSPATH%;

REM The directory of the .class files goes here

SET CLASSPATH=%CLASSPATH%;C:\Documents and Settings\Gonzalo Mejia\workspace\AFS Java\bin

REM %0 is the parameter 0 %1 is parameter 1, 2% is the parameter 2, etc.

java AFSMain %1 %2 %3

SET CLASSPATH=CLASSTEMP;

echo Program Finished

EXIT


