@echo off
echo ===================================================
echo   DEPLOIEMENT AUTOMATIQUE EVENTZELLA
echo ===================================================

echo.
echo [1/3] Compilation d'Angular (Frontend)...
cd ProjetBi-Front
call npm run build
if %errorlevel% neq 0 (
    echo Erreur lors de la compilation Angular !
    pause
    exit /b %errorlevel%
)
cd ..

echo.
echo [2/3] Reconstruction des images Docker...
call docker-compose build

echo.
echo [3/3] Redemarrage des serveurs...
call docker-compose up -d

echo.
echo ===================================================
echo   DEPLOIEMENT TERMINE AVEC SUCCES !
echo   Vous pouvez ouvrir http://localhost
echo ===================================================
pause
