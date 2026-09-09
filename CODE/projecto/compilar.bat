@echo off
REM ========================================
REM  Script de Compilación del Proyecto
REM ========================================

echo.
echo ========================================
echo   COMPILANDO PROYECTO
echo ========================================
echo.

REM Cambiar al directorio del script
cd /d "%~dp0"

REM Crear carpeta bin si no existe
if not exist "bin" (
    echo [*] Creando carpeta bin...
    mkdir bin
)

echo [*] Compilando archivos Java...
echo.

REM Compilar todos los archivos Java
javac -encoding UTF-8 -d bin -cp "lib/*" src/app/*.java src/controlador/*.java src/vista/*.java src/model/*.java src/dao/*.java src/daoJDBC/*.java src/db/*.java src/enumerativo/*.java src/gestion/*.java src/utilidades/*.java src/recursos/*.java

REM Verificar si la compilación fue exitosa
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   ✓ COMPILACION EXITOSA
    echo ========================================
    echo.
    echo Todos los archivos se compilaron correctamente.
    echo Los archivos .class están en la carpeta 'bin/'
    echo.
    echo Para ejecutar el programa, use: ejecutar.bat
    echo.
) else (
    echo.
    echo ========================================
    echo   ✗ ERROR DE COMPILACION
    echo ========================================
    echo.
    echo Hubo errores durante la compilación.
    echo Por favor, revise los mensajes de error anteriores.
    echo.
)

pause
