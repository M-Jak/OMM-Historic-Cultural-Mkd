FROM node:lts AS builder

COPY . /reactApp

WORKDIR /reactApp

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build


from nginx:1.25
COPY --from=builder /reactApp/build /usr/share/nginx/html/

# CMD ["nginx", "-g", "daemon off;"]