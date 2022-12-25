@echo off
c:
cd \ll3
del *.err
echo        Version number:                > sc.err
type version                              >> sc.err
echo        of Leisure Suit Larry 3:      >> sc.err
echo        was compiled at:              >> sc.err
tm start                                  >> sc.err
atr $$$*.* -rs
del $$$*.* 
del script\script.*
del script\text.*
del script\vocab.*
xcopy c:\system\script.* script
xcopy c:\system\vocab.*  script
xcopy c:\system\text.*   script
copy  c:\system\classdef
copy  c:\system\selector
call  vc                                  >> sc.err
c:\system\exe\sc @redo.lst -oScript -a -s >> sc.err
incver script\script.000 -vVersion        >> sc.err
del script\script.030                     >> sc.err
del script\script.14?                     >> sc.err
del script\script.15?                     >> sc.err
del script\script.16?                     >> sc.err
del script\script.341                     >> sc.err
c:\system\exe\sc 140.sc    -oScript -a -s >> sc.err
tm stop                                   >> sc.err



cd vols


REM	makevols.bat

:MakeIt360
echo  Beginning to make 360 floppies...
tm start                            > 360.err
cd 360
del resource.*
makevols LL3360 -F..\floppy.src -st >> ..\360.err
cd ..
echo     This was Version number:  >> 360.err
type ..\version                    >> 360.err
tm stop               				  >> 360.err

:MakeIt720
echo  Beginning to make 720 floppies...
tm start                            > 720.err
cd 720
del resource.*
makevols LL3720 -F..\floppy.src -st >> ..\720.err
cd ..
echo     This was Version number:  >> 720.err
type ..\version                    >> 720.err
tm stop               				  >> 720.err





vopt c:
vopt d:
vopt e:

call mirror

