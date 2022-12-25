pkzip f:SRC *.*
del   a:SRC.zip
dir f:
dir a:
pause
copy  f:SRC.zip  a:
del   f:SRC.zip

pkzip f:PIC PIC\*.*
del   a:PIC.zip
dir f:
dir a:
pause
copy  f:PIC.zip a:
del   f:PIC.zip

pkzip f:QA QA\*.*
del   a:QA.zip
dir f:
dir a:
pause
copy  f:QA.zip a:
del   f:QA.zip

pkzip f:SCRIPT SCRIPT\*.*
del   a:SCRIPT.zip
dir f:
dir a:
pause
copy  f:SCRIPT.zip a:
del   f:SCRIPT.zip

pkzip f:SOUND SOUND\*.*
del   a:SOUND.zip
dir f:
dir a:
pause
copy  f:SOUND.zip a:
del   f:SOUND.zip

pkzip f:TEST TEST\*.*
del   a:TEST.zip
dir f:
dir a:
pause
copy  f:TEST.zip a:
del   f:TEST.zip

pkzip f:VIEW VIEW\*.*
del   a:VIEW.zip
dir f:
dir a:
pause
copy  f:VIEW.zip a:
del   f:VIEW.zip

pkzip f:VOLS VOLS\*.*
del   a:VOLS.zip
dir f:
dir a:
pause
copy  f:VOLS.zip a:
del   f:VOLS.zip

pkzip f:DEMO VOLS\DEMO\*.bat
del   a:DEMO.zip
dir f:
dir a:
pause
copy  f:DEMO.zip a:
del   f:DEMO.zip

pkzip f:360 VOLS\360\*.bat
del   a:360.zip
dir f:
dir a:
pause
copy  f:360.zip a:
del   f:360.zip

pkzip f:720 VOLS\720\*.bat
del   a:720.zip
dir f:
dir a:
pause
copy  f:720.zip a:
del   f:720.zip

pkzip f:SYSTEM c:\system\*.*
del   a:SYSTEM.zip
dir f:
dir a:
pause
copy  f:SYSTEM.zip a:
del   f:SYSTEM.zip

pkzip f:EXE c:\system\exe\*.exe c:\system\exe\*.com c:\system\exe\*.bat c:\system\exe\*.doc
del   a:EXE.zip
dir f:
dir a:
pause
copy  f:EXE.zip a:
del   f:EXE.zip

echo DONE!
