@echo off
REM ========================================
REM  Script de Ejecución del Proyecto
REM ========================================

cd /d "%~dp0"

:MENU
cls
echo.
echo ========================================
echo   SISTEMA DE GESTION DE PELICULAS
echo ========================================
echo.
echo [1] Ejecutar aplicacion principal (GUI)
echo [2] Ejecutar demostracion de concurrencia
echo [3] Ejecutar cargador de peliculas (Thread)
echo [4] Ejecutar importador CSV
echo [5] Salir
echo.
echo ========================================
echo.

set /p opcion="Seleccione una opcion (1-5): "

if "%opcion%"=="1" goto OPCION1
if "%opcion%"=="2" goto OPCION2
if "%opcion%"=="3" goto OPCION3
if "%opcion%"=="4" goto OPCION4
if "%opcion%"=="5" goto SALIR

echo.
echo Opcion invalida. Intente nuevamente.
timeout /t 2 >nul
goto MENU

:OPCION1
cls
echo.
echo ========================================
echo   EJECUTANDO APLICACION PRINCIPAL
echo ========================================
echo.
java -cp "bin;lib/*" app.Main
echo.
pause
goto MENU

:OPCION2
cls
echo.
echo ========================================
echo   DEMOSTRACION DE CONCURRENCIA
echo ========================================
echo.
echo Este programa demuestra las 5 tecnicas de concurrencia:
echo 1. Timer/TimerTask - Auto-guardado
echo 2. ExecutorService - Busquedas paralelas
echo 3. extends Thread - Carga de peliculas
echo 4. SwingWorker - Importacion CSV
echo 5. SwingWorker + Timer - Pantalla de carga
echo.
java -cp "bin;lib/*" utilidades.DemostracionConcurrencia
echo.
pause
goto MENU

:OPCION3
cls
echo.
echo ========================================
echo   CARGADOR DE PELICULAS (Thread)
echo ========================================
echo.
echo Este programa demuestra la carga de peliculas
echo usando una clase que extiende Thread.
echo.
java -cp "bin;lib/*" utilidades.CargadorPeliculasThread
echo.
pause
goto MENU

:OPCION4
cls
echo.
echo ========================================
echo   IMPORTADOR CSV
echo ========================================
echo.
echo Este programa permite importar peliculas desde un archivo CSV.
echo Se mostrara una ventana para seleccionar el archivo.
echo.
echo Archivo de ejemplo: peliculas_ejemplo.csv
echo.
java -cp "bin;lib/*" utilidades.EjecutarImportadorCSV
echo.
pause
goto MENU

:SALIR
cls
echo.
echo ========================================
echo   Gracias por usar el sistema!
echo ========================================
echo.
timeout /t 2 >nul
exit
