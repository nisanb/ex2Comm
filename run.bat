@echo off
echo Activating Model Server
start _model.bat
timeout 2
echo Activating Controller Server
start _controller.bat
timeout 2
echo Activating View Server
start _view.bat