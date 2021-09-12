# base image
FROM node:12.21.0

# install chrome for protractor tests
# RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
# RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
# RUN apt-get update && apt-get install -yq google-chrome-stable

RUN mkdir -p /app

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH ./app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /app/package.json

RUN npm install
#RUN npm install --save-dev @angular-devkit/build-angular --force

# add app
COPY . /app

EXPOSE 4200

# start app
CMD ng serve --host 0.0.0.0 --port 4200
#CMD ["npm", "start"]
#RUN npm run build --dev
