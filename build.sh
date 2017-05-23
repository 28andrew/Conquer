#!/bin/bash
echo "Building Conquer Addon"
OFFLINE=false
for i in "$@" ; do
    if [[ $i == "offline" ]] ; then
        OFFLINE=true
    fi
done
if [ "$OFFLINE" = "true" ] ; then
    mvn -o clean install
else
    mvn clean install
fi
echo "Finished building Conquer Addon"
for i in "$@" ; do
    if [[ $i == "server" ]] ; then
        echo "Setting up the server"
        mkdir -p run
        mkdir -p run/plugins
        if [ ! -f run/spigot.jar ]; then
            echo "Spigot was not found, building now!"
            mkdir -p spigotbuild
            cd spigotbuild/
            curl -O https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
            java -jar BuildTools.jar
            cp spigot-*.jar ../run/spigot.jar
            cd ../
        fi
        cp plugin/target/Conquer.jar run/plugins/Conquer.jar
        echo "Finished setting up the server (make sure to install Skript also), run spigot.jar in the run/ directory (setup a run profile in your IDE)"
        break
    fi
done
