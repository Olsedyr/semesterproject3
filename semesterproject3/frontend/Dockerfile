# Use the official Node.js image as the base image
FROM node:20-alpine

# Set the working directory in the container
WORKDIR /frontend

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install npm dependencies
RUN npm install

# Copy the entire frontend directory contents to the working directory
COPY . .

# Expose port 3000 (default port for React apps)
EXPOSE 3000

# Command to start the React application
CMD ["npm", "start"]
