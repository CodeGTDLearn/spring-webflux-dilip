echo on
docker-compose -f ../test-compose.yml down
docker container prune --force
docker system prune --volumes --force
docker image rm pauloportfolio/web-api
docker system df
docker image ls
docker-compose -f ../test-compose.yml up --build --force-recreate
pause