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
xced pushb
c:\brief\b sc.err -m%game%keys
xced popb
echo Press any key to update the net, or Ctrl-Break to not.
pause
updnet
