@echo off
call gradlew.bat installDist
XCOPY "AtRaRaspPi\build\install" "R:\" /S /E /Y
