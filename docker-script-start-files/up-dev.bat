rem CLEAN EVERYTHIGN BEFORE START THE COMPOSE CONTAINERS
echo on
docker-compose -f ../dev-compose.yml down --remove-orphans
docker-compose -f ../test-compose.yml down --remove-orphans

docker container prune --force
docker container rm $(docker container ls -q)

docker system prune --volumes --force
docker volume rm $(docker volume ls -q)

docker image prune --force
docker image rm pauloportfolio/web-api

docker system df
docker image ls

rem START THE COMPOSE CONTAINERS
docker-compose -f ../dev-compose.yml up --build --force-recreate

pause
