@echo off

%~d0
cd %~dp0

echo === START MASTER_DWH ===

java -Xms256M -Xmx1024M ^
-Dtalend.component.manager.m2.repository="%cd%\lib" ^
-cp "master_dwh_0_1.jar;date_dwh_0_1.jar;category_dwh_0_1.jar;complain_dwh_0_1.jar;copy_of_service_dwh_0_1.jar;event_dwh_0_1.jar;evaluation_dwh_0_1.jar;localisation_dwh_0_1.jar;provider_dwh_0_1.jar;fact2_0_1.jar;scd_concurrence_dwh_0_1.jar;scd_ententainer_dwh_0_1.jar;subcategory_dwh_0_1.jar;trends_dwh_0_1.jar;venues_dwh_0_1.jar;weather_dwh_0_1.jar;beneficiary_0_1.jar;lib\*" ^
test.master_dwh_0_1.MASTER_DWH --context=Default

echo === END MASTER_DWH ===

pause
exit /b %ERRORLEVEL%