FROM node:18-alpine as builder

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build --prod

FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html


EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]
