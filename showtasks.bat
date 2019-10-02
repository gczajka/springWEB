call runcrud
if "%ERRORLEVEL%" == "0" goto startbrowser
echo runcrud command has errors - breaking work
goto fail

:startbrowser
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo starting browser failed
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Showtasks work is finished.