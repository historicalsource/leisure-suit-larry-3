echo off

REM 	Parameters are
REM 		%1:	drive on which to install the program
REM 		%2:	drive from which installation is taking place

REM	Confirm there is enough space available.
space %1: 2500kb
if errorlevel 1 goto NoSpace

REM	Create the subdirectory.
godir %1:\sierra\LSL3
if errorlevel 1 goto CanNotCreate

REM	Copy over the necessary files.
echo Copying drivers...
copy %2:*.drv       > NUL
copy %2:exists.com  > NUL
copy %2:__insth.bat > NUL

REM	Copy all the resources over.
__insth %1 %2

:NoSpace
echo "There is not enough space on %1 to install this game."
echo "LSL3 requires 2.5 megabytes of disk space."
pause
goto Exit

:CanNotCreate
echo "Unable to create directory %1."
pause
goto Exit

:Exit

