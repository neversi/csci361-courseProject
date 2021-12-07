.PHONY: all build push

build: 
	sh push_web.sh
	docker-compose up

make down:
	docker-compose down