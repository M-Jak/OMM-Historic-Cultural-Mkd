FROM node:20.11.0-alpine3.18

COPY . /reactApp

WORKDIR /reactApp


RUN npm install


COPY . .


EXPOSE 9091


CMD ["npm", "start"]
