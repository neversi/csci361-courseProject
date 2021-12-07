.PHONY: all build push rebuild

build: 
	sh push_web.sh
	docker-compose up

down:
	docker-compose down

rebuild:
	docker-compose down
	sh push_web.sh
	docker-compose up