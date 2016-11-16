for /f %%i in ('qwinsta ^| grep "^>" ^| awk "{print $3}"') do tscon %%i /dest:console
exit