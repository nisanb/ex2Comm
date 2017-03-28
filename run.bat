@echo off
Activating Model Server
start _model.bat
timeout 2
Activating Controller Server
start _controller.bat
timeout 2
Activating View Server
start _view.bat