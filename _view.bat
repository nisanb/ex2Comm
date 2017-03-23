:: creates bin directory if doesnt already exist.
@if not exist "%cd%\bin\" mkdir bin

:: copies other source folders contents into bin.
@xcopy /e /q /y resources bin\ > nul
@xcopy /e /q /y test bin\ > nul

:: compiles view layers java files into bin folder.
@javac -classpath lib/json-simple-1.1.1.jar -sourcepath src src/il/ac/haifa/is/datacomms/hw2/view/*.java -d bin

:: runs compiled model main class.
@java -cp bin;lib/json-simple-1.1.1.jar il.ac.haifa.is.datacomms.hw2.view.Main

:: pauses execution and displays "Press any key to continue...".
@pause