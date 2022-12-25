echo off

REM      Prompt for each disk of the game in succession,
REM   then copy everything from it.

echo Copying LSL3 files...

:Vol1
exists %2:resource.001
if not errorlevel 1 goto GetVol1
echo Please insert the disk labeled "Disk 1" in drive %2.
pause
goto Vol1
:GetVol1
copy %2:lsl3.bat .. > NUL
copy %2:readme.*    > NUL
copy %2:sierra.com  > NUL
copy %2:sciv.exe    > NUL
copy %2:install.*   > NUL
copy %2:resource.*  > NUL

:Vol2
exists %2:resource.002
if not errorlevel 1 goto GetVol2
echo Please insert the disk labeled "Disk 2" in drive %2.
pause
goto Vol2
:GetVol2
copy %2:*.* > NUL

:Vol3
exists %2:resource.003
if not errorlevel 1 goto GetVol3
echo Please insert the disk labeled "Disk 3" in drive %2.
pause
goto Vol3
:GetVol3
copy %2:*.* > NUL

:Vol4
exists %2:resource.004
if not errorlevel 1 goto GetVol4
echo Please insert the disk labeled "Disk 4" in drive %2.
pause
goto Vol4
:GetVol4
copy %2:*.* > NUL

:Vol5
exists %2:resource.005
if not errorlevel 1 goto GetVol5
echo Please insert the disk labeled "Disk 5" in drive %2.
pause
goto Vol5
:GetVol5
copy %2:*.* > NUL

:Vol6
exists %2:resource.006
if not errorlevel 1 goto GetVol6
echo Please insert the disk labeled "Disk 6" in drive %2.
pause
goto Vol6
:GetVol6
copy %2:*.* > NUL

:Vol7
exists %2:resource.007
if not errorlevel 1 goto GetVol7
echo Please insert the disk labeled "Disk 7" in drive %2.
pause
goto Vol7
:GetVol7
copy %2:*.* > NUL

:Vol8
exists %2:resource.008
if not errorlevel 1 goto GetVol8
echo Please insert the disk labeled "Disk 8" in drive %2.
pause
goto Vol8
:GetVol8
copy %2:*.* > NUL

cd ..

echo To start this game, type the following commands:
echo    %1:
echo    cd \Sierra 
echo    LSL3

