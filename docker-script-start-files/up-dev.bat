echo on
REM MAVEN-CLEAN-PACKAGE WITHOUT TESTS
cd ..
call mvn clean package -DskipTests
cd docker-script-start-files

REM DOCKER CLEAN-ALL ORPHANS
docker-compose -f ../dev-compose.yml down --remove-orphans
docker-compose -f ../test-compose.yml down --remove-orphans

docker container prune --force
docker container rm $(docker container ls -q)

docker system prune --volumes --force
docker volume rm $(docker volume ls -q)

docker image prune --force
docker image rm pauloportfolio/web-api

REM DOCKER LISTING IMAGES + SYSTEM
docker system df
docker image ls

REM START THE COMPOSE CONTAINERS
docker-compose -f ../dev-compose.yml up --build --force-recreate

pause
