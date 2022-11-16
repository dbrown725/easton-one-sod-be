# to make executable on mac "chmod 755 YourScriptName.sh"
# to run "./YourScriptName.sh"
cd target
exec java -Dspring.profiles.active=local -jar music-0.0.1-SNAPSHOT.jar