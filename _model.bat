:: creates bin directory if doesnt already exist.
@if not exist "%cd%\bin\" mkdir bin

:: copies other source folders contents into bin.
@xcopy /e /q /y resources bin\ > nul
@xcopy /e /q /y test bin\ > nul

:: compiles model layers java files into bin folder.
@javac -classpath lib/json-simple-1.1.1.jar -sourcepath src src/il/ac/haifa/is/datacomms/hw2/model/*.java -d bin

:: changes directory to bin folder.
@cd bin

:: runs RMI Registry on port 3000. must be run within classpath.
@start rmiregistry 3000

:: increase timeout time if your PC is too slow and java throws connect exceptions.
@timeout /t 1 > nul

:: changes directory back to project folder.
@cd ..

:: runs compiled model main class.
@java -cp bin;lib/json-simple-1.1.1.jar il.ac.haifa.is.datacomms.hw2.model.Main

:: pauses execution and displays "Press any key to continue...".
@pause