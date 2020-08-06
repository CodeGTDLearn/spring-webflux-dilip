echo on
docker-compose -f ../dev-compose.yml down
docker-compose -f ../dev-compose.yml down
docker container prune --force
docker system prune --volumes --force
docker image rm pauloportfolio/web-api
docker container rm $(docker container ls -q)
docker volume rm $(docker volume ls -q)
docker system df
docker image ls