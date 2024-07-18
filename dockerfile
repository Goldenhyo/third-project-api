FROM node:20.15 AS builder
RUN mkdir /app
WORKDIR /app
COPY package.json .
RUN npm install --force

COPY ./ ./
RUN npm run build
FROM nginx:latest
RUN rm /etc/nginx/conf.d/default.conf
RUN rm -rf /etc/nginx/conf.d/*
COPY ./nginx.conf /etc/nginx/conf.d/

COPY --from=builder app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
